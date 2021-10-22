package com.yy.micro.gateway.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @Author ywl
 * @Date 2021/10/18 17:19
 * @Description
 */
@Data
public class GatewayRouteInfo {
    private Long id;

    private String serviceId;

    private String uri;

    private String predicates;

    private String filters;

    private String sn;

    private Date createDate;

    private Date updateDate;

    private String remarks;

    private String delFlag;
}
