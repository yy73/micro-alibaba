package com.yy.micro.os.service;

import com.yy.micro.os.sentinel.OSServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author ywl
 * @Date 2021/9/28 15:09
 * @Description
 */
@FeignClient(value = "micro-producer", path = "/micro-producer", fallback = OSServiceFallback.class)
public interface OSService {

    @RequestMapping(value = "/rpc/getRpc2", method = RequestMethod.GET)
    String getRpc2(@RequestParam("param") String param);
}
