package org.example.springcloud.service;

import org.example.springcloud.hystrix.FallBack;
import org.example.springcloud.service.IService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "feign-client",fallback = FallBack.class)
public interface MyService extends IService {
}
