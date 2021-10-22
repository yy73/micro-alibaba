package com.yy.micro.gateway.mysql.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yy.micro.gateway.mysql.config.GatewayConfig;
import com.yy.micro.gateway.mysql.config.RouteHandler;
import com.yy.micro.gateway.mysql.entity.AppRoute;
import com.yy.micro.gateway.mysql.service.AppRouteService;
import com.yy.micro.gateway.mysql.service.RouteDefinitionCacheService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author ywl
 * @Date 2021/10/21 10:32
 * @Description
 */
@Service
public class RouteDefinitionCacheServiceImpl implements RouteDefinitionCacheService {
    private static final Logger logger = LoggerFactory.getLogger(RouteDefinitionCacheServiceImpl.class);
    /**
     * 本次缓存
     */
    private static ConcurrentHashMap<String, RouteDefinition> definitionMap = new ConcurrentHashMap<>();

    /**
     * redis 缓存地址
     */
    public static String SPACE = GatewayConfig.NACOS_DATA_ID + ":" + GatewayConfig.NACOS_GROUP_ID;

    @Autowired
    private RouteHandler routeHandler;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AppRouteService routeService;

    @Override
    public List<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> list = new ArrayList<>();
        if (definitionMap.size() > 0) {
            return new ArrayList<>(definitionMap.values());
        } else {
            redisTemplate.opsForHash().values(SPACE)
                    .stream().forEach(r -> {
                RouteDefinition route = JSONObject.parseObject(r.toString(), RouteDefinition.class);
                list.add(route);
                definitionMap.put(route.getId(), route);
            });
            return list;
        }
    }

    @Override
    public boolean saveAll(List<RouteDefinition> definitions) {
        if (definitions != null && definitions.size() > 0) {
            definitions.forEach(this::save);
            return true;
        }
        return false;
    }

    @Override
    public boolean has(String routeId) {
        return definitionMap.containsKey(routeId) ? true : redisTemplate.opsForHash().hasKey(SPACE, routeId);
    }

    @Override
    public boolean delete(String routeId) {
        logger.info("删除路由信息---》》 " + routeId);
        if (has(routeId)) {
            definitionMap.remove(routeId);
            redisTemplate.opsForHash().delete(SPACE, routeId);
            return true;
        }
        return false;
    }

    @Override
    public void clearAll() {
        Boolean delete = redisTemplate.delete(SPACE);
        System.out.println(delete);
    }

    @Override
    public boolean save(RouteDefinition routeDefinition) {
        logger.info("添加网关信息--》》 {} " + routeDefinition);
        if (routeDefinition != null && StringUtils.isNotBlank(routeDefinition.getId())) {
            definitionMap.put(routeDefinition.getId(), routeDefinition);
            redisTemplate.opsForHash().put(SPACE, routeDefinition.getId(), JSONObject.toJSONString(routeDefinition));
            return true;
        }
        return false;
    }

    @Override
    public boolean init() {
        logger.info("================= 开始 初始化 网关信息 ===============");
        List<AppRoute> routeList = routeService.findAll();
        if (routeList != null && routeList.size() > 0) {
            this.saveAll(routeList.stream().map(AppRoute::parseToRoute).collect(Collectors.toList()));
            routeHandler.refreshRoutes();
            return true;
        }
        return false;
    }
}
