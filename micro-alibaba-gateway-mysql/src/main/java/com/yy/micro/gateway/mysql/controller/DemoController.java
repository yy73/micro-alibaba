package com.yy.micro.gateway.mysql.controller;

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

    @GetMapping("/demo")
    public String demo() {
        return "micro-gateway-mysql";
    }
}
