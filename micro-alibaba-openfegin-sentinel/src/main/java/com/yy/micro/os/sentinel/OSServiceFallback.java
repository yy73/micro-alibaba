package com.yy.micro.os.sentinel;

import com.yy.micro.os.service.OSService;
import org.springframework.stereotype.Component;

/**
 * @Author ywl
 * @Date 2021/9/28 15:59
 * @Description
 */
@Component
public class OSServiceFallback implements OSService {
    @Override
    public String getRpc2(String param) {
        return "降级了！！！！！！！！！！！";
    }
}
