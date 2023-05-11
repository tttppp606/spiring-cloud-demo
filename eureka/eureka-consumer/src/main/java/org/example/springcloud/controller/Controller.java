package org.example.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.springcloud.entity.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class Controller {

    /**
     * 提供类注册中心注册的服务器信息
     */
    @Autowired
    private LoadBalancerClient client;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(){

        ServiceInstance instance = client.choose("eureka-client");

        if (instance == null){
            return "No available instances";
        }

        /**
         * 服务消费者通过eureka的注册中心（LoadBalancerClient）
         * 获取到类服务客户端的ip和端口，进行通讯
         */
        String target = String.format("http://%s:%s/sayHi",
                instance.getHost(),
                instance.getPort());

        log.info(target);

        /**
         * 参数：url和HTTP响应转换被转换成的对象类型
         */
        return restTemplate.getForObject(target,String.class);
    }

    @PostMapping("/hello")
    public Friend helloPost(){

        ServiceInstance instance = client.choose("eureka-client");

        if (instance == null){
            return null;
        }

        String target = String.format("http://%s:%s/sayHi",
                instance.getHost(),
                instance.getPort());

        log.info(target);

        Friend friend = new Friend();
        friend.setName("Eureka-Consumer");

        /**
         * 参数：url和HTTP响应转换被转换成的对象类型
         */
        return restTemplate.postForObject(target,friend,Friend.class);
    }
}
