package com.yy.micro.gateway.mysql.service;

import com.yy.micro.gateway.mysql.entity.AppRoute;

import java.util.List;

/**
 * @Author ywl
 * @Date 2021/10/21 11:06
 * @Description
 */
public interface AppRouteService {
    List<AppRoute> findAll();

    boolean saveOrUpdate(AppRoute route);

    boolean save(AppRoute route);

    boolean update(AppRoute route);

    boolean delete(String routeId);

    AppRoute findByRouteId(String routeId);

    AppRoute findById(Integer id);
}
