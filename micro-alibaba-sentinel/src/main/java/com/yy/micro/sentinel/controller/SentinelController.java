package com.yy.micro.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ywl
 * @Date 2021/9/27 16:50
 * @Description
 */
@RestController
public class SentinelController {

    @GetMapping("/demo")
    public String demo() {
        return "hello sentinel";
    }

    /**
     * 基于 qps
     *
     * @return
     */
    @GetMapping("/flow")
    @SentinelResource(value = "flow", blockHandler = "flowBlockHandler", fallback = "fall")
    public String flow() {
        int a = 1 / 0;
        return "正常访问-qps";
    }

    /**
     * 基于 线程数
     *
     * @return
     */
    @GetMapping("/flowThread")
    @SentinelResource(value = "flowThread", blockHandler = "flowBlockHandler")
    public String flowThread() throws InterruptedException {
        Thread.sleep(5000);
        return "正常访问-线程数";
    }


    @GetMapping("/getOrder")
    @SentinelResource(value = "getOrder", blockHandler = "flowBlockHandler")
    public String getOrder() {
        return "获取订单";
    }

    @GetMapping("/addOrder")
    @SentinelResource(value = "addOrder", blockHandler = "flowBlockHandler")
    public String addOrder() {
        return "添加订单";
    }

    public String flowBlockHandler(BlockException e) {
        return "被流控了";
    }

    public String fall() {
        return "被降级了";
    }
}
