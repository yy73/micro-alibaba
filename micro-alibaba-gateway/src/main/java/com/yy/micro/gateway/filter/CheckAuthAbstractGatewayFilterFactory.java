package com.yy.micro.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

/**
 * @Author ywl
 * @Date 2021/9/22 16:32
 * @Description
 */
public class CheckAuthAbstractGatewayFilterFactory extends AbstractGatewayFilterFactory<CheckAuthAbstractGatewayFilterFactory> {

    @Override
    public GatewayFilter apply(CheckAuthAbstractGatewayFilterFactory config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                return null;
            }
        };
    }


    @Override
    public String name() {
        return null;
    }


    @Override
    public ShortcutType shortcutType() {
        return null;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return null;
    }

    @Override
    public String shortcutFieldPrefix() {
        return null;
    }
}
