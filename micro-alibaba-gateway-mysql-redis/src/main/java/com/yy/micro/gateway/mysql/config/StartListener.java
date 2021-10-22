package com.yy.micro.gateway.mysql.config;

import com.yy.micro.gateway.mysql.entity.AppRoute;
import com.yy.micro.gateway.mysql.service.AppRouteService;
import com.yy.micro.gateway.mysql.service.RouteDefinitionCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author ywl
 * @Date 2021/10/21 11:28
 * @Description
 */
@Component
public class StartListener {

    private static final Logger logger = LoggerFactory.getLogger(StartListener.class);

    @Autowired
    private RouteDefinitionCacheService cacheService;

    @Autowired
    private AppRouteService routeService;

    @PostConstruct
    public void init() {
        logger.info("初始化路由数据...");

        List<AppRoute> routeList = routeService.findAll();
        if (routeList != null && routeList.size() > 0) {
            cacheService.clearAll();
            cacheService.saveAll(routeList.stream().map(AppRoute::parseToRoute).collect(Collectors.toList()));
        }
    }
}
