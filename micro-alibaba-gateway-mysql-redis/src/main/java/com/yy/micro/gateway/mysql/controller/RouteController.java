package com.yy.micro.gateway.mysql.controller;


import com.yy.micro.gateway.mysql.entity.AppRoute;
import com.yy.micro.gateway.mysql.entity.JsonResult;
import com.yy.micro.gateway.mysql.service.AppRouteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ywl
 * @Date 2021/10/18 17:50
 * @Description
 */
@RestController
@RequestMapping("/route")
public class RouteController {
    @Autowired
    private AppRouteService appRouteService;

    @GetMapping(value = "list")
    public JsonResult list() {
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.put("routeList", appRouteService.findAll());
        return jsonResult;
    }

    @PostMapping(value = "/save")
    public JsonResult save(AppRoute route) {
        Assert.hasText(route.getRouteId(), "routerID不能为空");
        Assert.hasText(route.getUri(), "uri不能为空");

//        if (route == null || StringUtils.isBlank(route.getRouteId())) {
//            return new JsonResult(false, "id不能为空");
//        } else if (StringUtils.isBlank(route.getUri())) {
//            return new JsonResult(false, "uri不能为空");
//        }

//        AppRoute oldRoute = null;
//        if (route.getId() != null) {
//            oldRoute = appRouteService.findById(route.getId());
//            if (oldRoute == null || oldRoute.getId() == null) {
//                return new JsonResult(false, "数据不存在或已被删除");
//            }
//        }
//
//        AppRoute sameRouteIdObj = appRouteService.findByRouteId(route.getRouteId());
//        if (sameRouteIdObj != null && sameRouteIdObj.getId() != null) {
//            if (route.getId() == null) {
//                return new JsonResult(false, "已存在相同 RouteId 的配置");
//            }
//        }
        route.setPredicates(route.getPredicates() != null ? route.getPredicates().trim() : null);
        route.setFilters(route.getFilters() != null ? route.getFilters().trim() : null);

        boolean res = appRouteService.save(route);
        return new JsonResult(res, res ? "操作成功" : "操作失败");
    }

    @PostMapping("/update")
    public JsonResult update(@RequestBody AppRoute appRoute) {
        boolean update = appRouteService.update(appRoute);
        return new JsonResult(update, update ? "操作成功" : "操作失败");
    }

    @GetMapping(value = "/delete")
    public JsonResult delete(String routeId) {
        AppRoute route = appRouteService.findByRouteId(routeId);
        if (route == null || StringUtils.isBlank(route.getRouteId())) {
            return new JsonResult(false, "路由不存在");
        }

        boolean res = appRouteService.delete(routeId);
        return new JsonResult(res, res ? "操作成功" : "操作失败");
    }
}
