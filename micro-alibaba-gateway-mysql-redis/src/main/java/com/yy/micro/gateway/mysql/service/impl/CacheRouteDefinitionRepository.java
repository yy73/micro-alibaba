package com.yy.micro.gateway.mysql.service.impl;

import com.yy.micro.gateway.mysql.service.RouteDefinitionCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Author ywl
 * @Date 2021/10/21 10:41
 * @Description
 */
@Service
public class CacheRouteDefinitionRepository implements RouteDefinitionRepository {

    @Autowired
    private RouteDefinitionCacheService routeDefinitionCacheService;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routeDefinitions = routeDefinitionCacheService.getRouteDefinitions();
        return Flux.fromIterable(routeDefinitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            routeDefinitionCacheService.save(r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (routeDefinitionCacheService.has(id)) {
                routeDefinitionCacheService.delete(id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("未找到路由配置: " + routeId)));
        });
    }
}
