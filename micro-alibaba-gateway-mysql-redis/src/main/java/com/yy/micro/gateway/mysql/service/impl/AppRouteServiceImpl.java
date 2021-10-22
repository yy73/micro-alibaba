package com.yy.micro.gateway.mysql.service.impl;

import cn.hutool.core.util.IdUtil;
import com.yy.micro.gateway.mysql.config.RouteHandler;
import com.yy.micro.gateway.mysql.entity.AppRoute;
import com.yy.micro.gateway.mysql.mapper.AppRouteMapper;
import com.yy.micro.gateway.mysql.service.AppRouteService;
import com.yy.micro.gateway.mysql.service.RouteDefinitionCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author ywl
 * @Date 2021/10/21 11:24
 * @Description
 */
@Service
public class AppRouteServiceImpl implements AppRouteService {
    private static final Logger logger = LoggerFactory.getLogger(AppRouteService.class);

    @Autowired
    private AppRouteMapper appRouteDAO;

    @Autowired
    private RouteDefinitionCacheService cacheService;

    @Autowired
    private RouteHandler routeHandler;

    @Override
    public List<AppRoute> findAll() {
        return appRouteDAO.findAll();
    }

    @Override
    public boolean saveOrUpdate(AppRoute route) {
        route.setUpdateTime(new Date());
        AppRoute oldRoute = appRouteDAO.findById(route.getId());
        boolean res = false;
        if (oldRoute != null && oldRoute.getId() != null) {
            res = appRouteDAO.update(route);
        } else {
            res = appRouteDAO.insert(route);
        }

        if (res) {
            logger.info("更新缓存，通知网关重新加载路由信息...");
            cacheService.save(route.parseToRoute());
            routeHandler.refreshRoutes();
        }

        return res;
    }

    @Override
    public boolean save(AppRoute route) {
        route.setId(IdUtil.getSnowflake(3, 1).hashCode());
        route.setUpdateTime(new Date());
        boolean insert = appRouteDAO.insert(route);
        if (insert) {
            cacheService.save(route.parseToRoute());
            routeHandler.refreshRoutes();
        }
        return insert;
    }

    @Override
    public boolean update(AppRoute route) {
        route.setUpdateTime(new Date());
        boolean update = appRouteDAO.update(route);
        if (update) {
            cacheService.init();
        }
        return update;
    }

    @Override
    public boolean delete(String routeId) {
        boolean res = appRouteDAO.delete(routeId);
        cacheService.delete(routeId);
//        if (res) {
//            logger.info("更新缓存，通知网关重新加载路由信息...");
//            cacheService.init();
//
//        }
        return res;
    }

    @Override
    public AppRoute findByRouteId(String routeId) {
        return appRouteDAO.findByRouteId(routeId);
    }

    @Override
    public AppRoute findById(Integer id) {
        return appRouteDAO.findById(id);
    }
}
