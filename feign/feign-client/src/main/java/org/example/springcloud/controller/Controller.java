package org.example.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.springcloud.entity.Friend;
import org.example.springcloud.service.IService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *  IService作用：
 *  利用feign，会在服务调用者代码里增加一个IService接口，必须将目标服务节点的serverNamne和path重新配置一次，
 *  相当于同一个配置在服务节点和调用方各写了一次，所以此模块就是把这些配置提取到一个公共模块feign-client-intf中，
 *  服务方的controller继承该接口IService，调用方@Autowired该接口，实现同一个配置只写一次
 */
@RestController
@Slf4j
public class Controller implements IService {

    @Value("${server.port}")
    private String port;
    @Override
    public String sayHi() {
        return "This is " + port;
    }

    @Override
    public Friend sayHiPost(Friend friend) {
        log.info("You are" + friend.getName());
        friend.setPort(port);
        return friend;
    }

    @Override
    public String retry(int timeout) {
        while (timeout-- >= 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
        log.info("retry" + port);
        return port;
    }

    @Override
    public String error() {
        throw new RuntimeException("black sheep");
    }
}
