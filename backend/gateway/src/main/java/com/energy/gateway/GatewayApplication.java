package com.energy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 认证服务路由
                .route("auth-service", r -> r.path("/api/auth/**")
                        .uri("lb://auth-service"))
                // 设备服务路由
                .route("device-service", r -> r.path("/api/devices/**")
                        .uri("lb://device-service"))
                // 数据采集服务路由
                .route("data-collection-service", r -> r.path("/api/data/collect/**")
                        .uri("lb://data-collection-service"))
                // 数据处理服务路由
                .route("data-process-service", r -> r.path("/api/data/process/**")
                        .uri("lb://data-process-service"))
                // 数据存储服务路由
                .route("data-storage-service", r -> r.path("/api/data/storage/**")
                        .uri("lb://data-storage-service"))
                // 告警服务路由
                .route("alert-service", r -> r.path("/api/alerts/**")
                        .uri("lb://alert-service"))
                .build();
    }
}