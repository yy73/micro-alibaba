package com.yy.micro.gateway.mysql.mapper;

import com.yy.micro.gateway.mysql.entity.AppRoute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author ywl
 * @Date 2021/10/21 11:10
 * @Description
 */
@Mapper
public interface AppRouteMapper {

    @Select("select * from app_route")
    List<AppRoute> findAll();

    @Select("select * from app_route where routeId = #{routeId}")
    AppRoute findByRouteId(String routeId);

    @Select("select * from app_route where id = #{id}")
    AppRoute findById(Integer id);

    boolean update(AppRoute route);

    boolean insert(AppRoute route);

    boolean delete(@Param("routeId") String routeId);
}
