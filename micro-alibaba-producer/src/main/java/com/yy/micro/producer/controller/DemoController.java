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

    @GetMapping("/getProducer")
    public String getProducer() {
        return "micro alibaba getProducer";
    }

    @GetMapping("/addProducer")
    public String addProducer() {
        return "micro alibaba addProducer";
    }

    @GetMapping("/delProducer")
    public String delProducer() {
        return "micro alibaba delProducer";
    }
}
