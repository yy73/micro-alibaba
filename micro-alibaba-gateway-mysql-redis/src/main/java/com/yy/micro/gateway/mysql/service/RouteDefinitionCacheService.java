package com.yy.micro.gateway.mysql.service;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * @Author ywl
 * @Date 2021/10/21 10:31
 * @Description
 */
public interface RouteDefinitionCacheService {
    List<RouteDefinition> getRouteDefinitions();

    boolean saveAll(List<RouteDefinition> definitions);

    boolean has(String routeId);

    boolean delete(String routeId);

    void clearAll();

    boolean save(RouteDefinition routeDefinition);

    boolean init();

}
