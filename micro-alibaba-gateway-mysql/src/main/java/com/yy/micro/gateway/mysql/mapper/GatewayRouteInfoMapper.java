package com.yy.micro.gateway.mysql.mapper;

import com.yy.micro.gateway.mysql.entity.GatewayRouteInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ywl
 * @Date 2021/10/18 17:36
 * @Description
 */
@Mapper
public interface GatewayRouteInfoMapper {

    List<GatewayRouteInfo> queryAllRoutes();

    int insert(GatewayRouteInfo gatewayRouteInfo);

    int update(GatewayRouteInfo gatewayRouteInfo);

    void delByRouterId(@Param("routerId") String routerId);


}
