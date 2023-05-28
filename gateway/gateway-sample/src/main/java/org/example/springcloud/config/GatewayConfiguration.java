package org.example.springcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

import java.time.ZonedDateTime;

/**
 * Gateway的java代码配置，跟配置文件配置一样
 */
@Configuration
public class GatewayConfiguration {

    /**
     * 自定义的过滤器实现接口计时功能
     * 效果：
     * 2023-05-27 09:51:14.294  INFO 93974 --- [ctor-http-nio-7] o.e.springcloud.config.TimerFilter       : StopWatch '': running time (millis) = 12
     * -----------------------------------------
     * ms     %     Task name
     * -----------------------------------------
     * 00012  100%  /sayHi
     */
    @Autowired
    private TimerFilter timerFilter;

    @Autowired
    private AuthFilter authFilter;

    @Bean
    @Order
    public RouteLocator customizedRoutes(RouteLocatorBuilder/*上下文中会加载的*/ builder) {
        return builder.routes()
                .route(r -> r.path("/java/**")   //以/java/开头的请求可以匹配该规则
                    .and().method(HttpMethod.GET)   //get请求可以匹配该规则
                    .and().header("name")           //header中必须有name这个key
                    .filters(f -> f.stripPrefix(1)   //localhost:1008/yml/sayHi被转发后去访问feign-client服务的/yml/sayHi,
                                                           //stripPrefix的作用是把第一个前缀yml删掉，变为访问feign-client服务的/sayHi
                            .addResponseHeader("java-param", "gateway-config")
                            .filter(timerFilter)//接口计时过滤器
                            .filter(authFilter) //权限验证过滤器
                    )
                    .uri("lb://FEIGN-CLIENT")
                )
                .route(r -> r.path("/seckill/**")
                        .and().after(ZonedDateTime.now().plusMinutes(1))
//                        .and().before()
//                        .and().between()
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://FEIGN-CLIENT")
                )
                .build();
    }
}
