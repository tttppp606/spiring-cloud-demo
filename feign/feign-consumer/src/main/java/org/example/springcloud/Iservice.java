package org.example.springcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * feign    核心配置接口
 */
@FeignClient("eureka-client")/* 属性为客户端的serverName  */
public interface Iservice {

    @GetMapping("/sayHi")
    String sayHi();
}
