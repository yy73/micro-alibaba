package com.yy.micro.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ywl
 * @Date 2021/9/18 11:10
 * @Description
 */
@RestController
@RequestMapping("/rpc")
public class RpcController {

    @GetMapping("/getRpc")
    public String getRpc(String param) {
        return "getRpc: " + param;
    }
}
