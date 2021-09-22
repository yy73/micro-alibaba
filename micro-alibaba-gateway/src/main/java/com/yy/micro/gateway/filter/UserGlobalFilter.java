package com.yy.micro.gateway.filter;

import io.netty.handler.codec.http.HttpContentEncoder;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

/**
 * @Author ywl
 * @Date 2021/9/22 16:43
 * @Description
 */
@Component
public class UserGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        if (path.contains("demo1")) {
            return getVoidMono(exchange, "hhhh");
        }
        System.out.println(path);
        return chain.filter(exchange);
    }

    // 拦截配置
    @NotNull
    private Mono<Void> getVoidMono(ServerWebExchange exchange, String msg) {

        final ServerHttpResponse response = exchange.getResponse();
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));//设置body
    }


}
