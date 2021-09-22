package com.yy.micro.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author ywl
 * @Date 2021/9/18 10:54
 * @Description
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.yy.micro.producer.rpc"})
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
