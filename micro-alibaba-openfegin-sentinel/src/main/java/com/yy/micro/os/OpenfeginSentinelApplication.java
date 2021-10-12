package com.yy.micro.os;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author ywl
 * @Date 2021/9/28 15:08
 * @Description
 */
@SpringBootApplication
@EnableFeignClients
public class OpenfeginSentinelApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenfeginSentinelApplication.class, args);
    }
}
