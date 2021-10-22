package com.yy.micro.gateway.mysql.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @Author ywl
 * @Date 2021/10/21 11:01
 * @Description
 */
@Component
public class RouteHandler implements ApplicationEventPublisherAware {

    private static final Logger log = LoggerFactory.getLogger(RouteHandler.class);

    private ApplicationEventPublisher applicationEventPublisher;

//    @Override
//    public void run(String... args) throws Exception {
//
//    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void refreshRoutes() {
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
