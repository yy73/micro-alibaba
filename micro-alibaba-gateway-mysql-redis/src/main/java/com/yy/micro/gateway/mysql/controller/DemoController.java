package com.yy.micro.gateway.mysql.controller;

import com.yy.micro.gateway.mysql.config.GatewayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ywl
 * @Date 2021/10/15 17:01
 * @Description
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private RedisTemplate redisTemplate;
    public static String SPACE = GatewayConfig.NACOS_DATA_ID + ":" + GatewayConfig.NACOS_GROUP_ID;

    @GetMapping("/demo")
    public String demo() {
        redisTemplate.delete(SPACE);
        return "micro-gateway-mysql";
    }
}
