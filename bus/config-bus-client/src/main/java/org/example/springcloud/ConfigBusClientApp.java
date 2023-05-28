package org.example.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 动态刷新配置触发：
 * 1、访问配置中心的/actuator/bus-refresh
 * http://localhost:60002/actuator/bus-refresh动态刷新
 * 2、访问任意客户端的/actuator/bus-refresh
 * http://localhost:61002/actuator/bus-refresh动态刷新
 */
@SpringBootApplication
@EnableDiscoveryClient//配置中心基于eureka的高可用改造
public class ConfigBusClientApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigBusClientApp.class).
                web(WebApplicationType.SERVLET).
                run(args);
    }
}
