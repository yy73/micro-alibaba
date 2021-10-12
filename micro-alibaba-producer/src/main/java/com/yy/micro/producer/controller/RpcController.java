package com.yy.micro.producer.controller;

import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
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
    @Trace
    @Tags({@Tag(key = "getRpc", value = "returnedObj"),
            @Tag(key = "param", value = "arg[0]")})
    public String getRpc(String param) {
        return "getRpc: " + param;
    }


    @GetMapping("/getRpc2")
    public String getRpc2(String param) {
//        int a = 1 / 0;
        return "getRpc2: " + param;
    }
}
