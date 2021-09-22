package com.yy.micro.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ywl
 * @Date 2021/9/18 10:48
 * @Description
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/demo")
    public String demo() {
        return "micro alibaba producer";
    }
}
