package com.yy.micro.gateway.mysql.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ywl
 * @Date 2021/10/21 10:34
 * @Description
 */
@Configuration
public class GatewayConfig {
    public static String NACOS_DATA_ID;
    public static String NACOS_GROUP_ID;

    @Value("${gateway.dynamicRoute.dataId}")
    public void setNacosDataId(String dataId) {
        NACOS_DATA_ID = dataId;
    }

    @Value("${gateway.dynamicRoute.group}")
    public void setNacosGroupId(String group) {
        NACOS_GROUP_ID = group;
    }
}
