package com.yy.micro.gateway.mysql.controller;

import com.yy.micro.gateway.mysql.entity.GatewayRouteInfo;
import com.yy.micro.gateway.mysql.handler.GatewayServiceHandler;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ywl
 * @Date 2021/10/18 17:50
 * @Description
 */
@RestController
@RequestMapping("/route")
public class RouteController {
    @Autowired
    private GatewayServiceHandler gatewayServiceHandler;

    /**
     * 刷新路由配置
     *
     * @param gwdefinition
     * @return
     */
    @GetMapping("/refresh")
    public void refresh() throws Exception {
        this.gatewayServiceHandler.loadRouteConfig();
    }

    @GetMapping("/routes")
    public void routes() throws Exception {
        List<GatewayRouteInfo> gatewayRouteInfos = gatewayServiceHandler.queryAllRoutes();
    }

    @PostMapping("/saveRoute")
    public void saveRoute(@RequestBody GatewayRouteInfo gatewayRouteInfo) {
        gatewayServiceHandler.saveRoute(gatewayRouteInfo);
    }

    @GetMapping("/deleteRoute")
    public void deleteRoute(String routeId) {
        gatewayServiceHandler.deleteRoute(routeId);
    }
}
