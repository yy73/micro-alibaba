package com.yy.micro.producer.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author ywl
 * @Date 2021/9/18 11:07
 * @Description
 */
@FeignClient(value = "micro-producer", path = "/micro-producer")
public interface ProducerRpcService {

    @RequestMapping(value = "/rpc/getRpc", method = RequestMethod.GET)
    String getRpc(@RequestParam("param") String param);
}
