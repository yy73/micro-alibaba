package com.yy.micro.consumer.controller;

import com.yy.micro.producer.rpc.ProducerRpcService;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ywl
 * @Date 2021/9/18 10:54
 * @Description
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private ProducerRpcService producerRpcService;

    @GetMapping("/demo")
    public String demo() {
        return "consumer micro consumber";
    }

    @GetMapping("/demo2")
    @Trace
    @Tags({@Tag(key = "demo2", value = "returnedObj"),
            @Tag(key = "param", value = "arg[0]")})
    public String demo2(String param) {
        return producerRpcService.getRpc(param);
    }
}
