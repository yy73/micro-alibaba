package com.yy.micro.gateway.mysql.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.yy.micro.gateway.mysql.entity.GatewayRouteInfo;
import com.yy.micro.gateway.mysql.mapper.GatewayRouteInfoMapper;
import com.yy.micro.gateway.mysql.repository.RedisRouteDefinitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

/**
 * @Author ywl
 * @Date 2021/10/18 17:31
 * @Description 开启事件监听，并且在项目启动的时候初始化
 */
@Slf4j
@Service
public class GatewayServiceHandler implements ApplicationEventPublisherAware, CommandLineRunner {

    @Autowired
    private RedisRouteDefinitionRepository redisRouteDefinitionRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private GatewayRouteInfoMapper gatewayRouteInfoMapper;

    private ApplicationEventPublisher publisher;

    @Override
    public void run(String... args) throws Exception {
        this.loadRouteConfig();
    }

    public String loadRouteConfig() {
        log.info("====开始加载=====网关配置信息=========");
        // 删除redis里面的路由配置信息
        redisTemplate.delete(RedisRouteDefinitionRepository.GATEWAY_ROUTES);

        // 从数据库拿到基本路由配置
        List<GatewayRouteInfo> gatewayRouteList = gatewayRouteInfoMapper.queryAllRoutes();
        gatewayRouteList.forEach(gatewayRoute -> {
            RouteDefinition definition = handleData(gatewayRoute);
            redisRouteDefinitionRepository.save(Mono.just(definition)).subscribe();
        });

        this.publisher.publishEvent(new RefreshRoutesEvent(this));

        log.info("=======网关配置信息===加载完成======");
        return "success";
    }

    /**
     * 查询所有已经加载的路由
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<GatewayRouteInfo> queryAllRoutes() {
        List<GatewayRouteInfo> gatewayRouteInfos = new ArrayList<GatewayRouteInfo>();
        redisTemplate.opsForHash().values(RedisRouteDefinitionRepository.GATEWAY_ROUTES).stream()
                .forEach(routeDefinition -> {
                    RouteDefinition definition = JSON.parseObject(routeDefinition.toString(), RouteDefinition.class);
                    gatewayRouteInfos.add(convert2GatewayRouteInfo(definition));
                });
        return gatewayRouteInfos;
    }

    /**
     * 将redis中路由信息转换为返回给前端的路由信息
     *
     * @param obj
     * @return
     */
    private GatewayRouteInfo convert2GatewayRouteInfo(Object obj) {
        RouteDefinition routeDefinition = (RouteDefinition) obj;
        GatewayRouteInfo gatewayRouteInfo = new GatewayRouteInfo();
        gatewayRouteInfo.setUri(routeDefinition.getUri().toString());
        gatewayRouteInfo.setServiceId(routeDefinition.getId());
        List<PredicateDefinition> predicates = routeDefinition.getPredicates();
        // 只有一个
        if (CollectionUtils.isNotEmpty(predicates)) {
            String predicatesString = predicates.get(0).getArgs().get("pattern");
            gatewayRouteInfo.setPredicates(predicatesString);
        }
        List<FilterDefinition> filters = routeDefinition.getFilters();
        if (CollectionUtils.isNotEmpty(filters)) {
            String filterString = filters.get(0).getArgs().get("_genkey_0");
            gatewayRouteInfo.setFilters(filterString);
        }
        gatewayRouteInfo.setSn(String.valueOf(routeDefinition.getOrder()));
        return gatewayRouteInfo;
    }

    /**
     * 添加 路由信息
     *
     * @param gatewayRouteInfo
     */
    public void saveRoute(GatewayRouteInfo gatewayRouteInfo) {
        RouteDefinition definition = handleData(gatewayRouteInfo);
        redisRouteDefinitionRepository.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));

        gatewayRouteInfoMapper.insert(gatewayRouteInfo);
    }

    /**
     * 更新路由信息
     *
     * @param gatewayRouteInfo
     */
    public void update(GatewayRouteInfo gatewayRouteInfo) {
        RouteDefinition definition = handleData(gatewayRouteInfo);
        try {
            this.redisRouteDefinitionRepository.delete(Mono.just(definition.getId()));
            redisRouteDefinitionRepository.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));

            gatewayRouteInfoMapper.update(gatewayRouteInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除路由信息
     *
     * @param routeId
     */
    public void deleteRoute(String routeId) {
        redisRouteDefinitionRepository.delete(Mono.just(routeId)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));

        gatewayRouteInfoMapper.delByRouterId(routeId);
    }

    /**
     * 路由数据转换公共方法
     *
     * @param gatewayRouteInfo
     * @return
     */
    private RouteDefinition handleData(GatewayRouteInfo gatewayRouteInfo) {
        RouteDefinition definition = new RouteDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);
        PredicateDefinition predicate = new PredicateDefinition();
        FilterDefinition filterDefinition = new FilterDefinition();
        Map<String, String> filterParams = new HashMap<>(8);

        URI uri = null;
        if (gatewayRouteInfo.getUri().startsWith("http")) {
            // http地址
            uri = UriComponentsBuilder.fromHttpUrl(gatewayRouteInfo.getUri()).build().toUri();
        } else {
            // 注册中心
            uri = UriComponentsBuilder.fromUriString("lb://" + gatewayRouteInfo.getUri()).build().toUri();
        }

        definition.setId(gatewayRouteInfo.getServiceId());
        // 名称是固定的，spring gateway会根据名称找对应的PredicateFactory
        predicate.setName("Path");
        predicateParams.put("pattern", gatewayRouteInfo.getPredicates());
        predicate.setArgs(predicateParams);

        // 名称是固定的, 路径去前缀
        filterDefinition.setName("StripPrefix");
        filterParams.put("_genkey_0", gatewayRouteInfo.getFilters().toString());
        filterDefinition.setArgs(filterParams);

        definition.setPredicates(Arrays.asList(predicate));
        definition.setFilters(Arrays.asList(filterDefinition));
        definition.setUri(uri);
        definition.setOrder(Integer.parseInt(gatewayRouteInfo.getSn()));

        return definition;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
