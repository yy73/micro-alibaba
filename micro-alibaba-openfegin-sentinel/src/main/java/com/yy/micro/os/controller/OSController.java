package com.yy.micro.os.controller;

import com.yy.micro.os.service.OSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ywl
 * @Date 2021/9/28 15:13
 * @Description
 */
@RestController
public class OSController {

    @Autowired
    private OSService osService;


    @GetMapping("/getRpc2")
    public String getRpc2(String param) {
        return osService.getRpc2(param);
    }
}
