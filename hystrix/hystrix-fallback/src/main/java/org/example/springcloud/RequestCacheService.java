package org.example.springcloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import lombok.extern.slf4j.Slf4j;
import org.example.springcloud.entity.Friend;
import org.example.springcloud.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Service
public class RequestCacheService {

    @Autowired
    private MyService myService;


    @CacheResult
    @HystrixCommand(commandKey = "cacheKey")// 可以作为一个标识，用于在yml中配置特定方法的hystrix设置
    public Friend requestCache(@CacheKey String name){
        log.info("request cache" + name);
        Friend friend = new Friend();
        friend.setName(name);
        friend = myService.sayHiPost(friend);
        log.info("after request cache" + name);
        return friend;
    }
}
