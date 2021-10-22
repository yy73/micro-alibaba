package com.yy.micro.gateway.mysql.entity;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.Date;
import java.util.List;

/**
 * @Author ywl
 * @Date 2021/10/21 11:07
 * @Description
 */
public class AppRoute {
    private Integer id;
    private String routeId;
    private Integer order;
    private String uri;
    private String predicates; // json
    private String filters; // json
    private Date updateTime;
    //private Integer updateUserId;
    private Boolean delete; // 是否删除

    public AppRoute() {
    }

    public AppRoute(String routeId, Integer order, String uri) {
        this.routeId = routeId;
        this.order = order;
        this.uri = uri;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPredicates() {
        return predicates;
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public RouteDefinition parseToRoute() throws JSONException {
        if (StringUtils.isBlank(routeId) || StringUtils.isBlank(uri)) {
            return null;
        }

        List<PredicateDefinition> predicateDefinitions = StringUtils.isBlank(predicates)
                ? null : JSONObject.parseArray(predicates, PredicateDefinition.class);

        List<FilterDefinition> filterDefinitions = StringUtils.isBlank(filters)
                ? null : JSONObject.parseArray(filters, FilterDefinition.class);

        AppRoute routeForCopy = new AppRoute("", order, uri);
        RouteDefinition routeDefinition = JSONObject.parseObject(JSONObject.toJSONString(routeForCopy), RouteDefinition.class);
        routeDefinition.setId(routeId);
        routeDefinition.setPredicates(predicateDefinitions);
        routeDefinition.setFilters(filterDefinitions);

        return routeDefinition;
    }
}
