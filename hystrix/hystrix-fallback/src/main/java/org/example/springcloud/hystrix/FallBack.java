package org.example.springcloud.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.example.springcloud.service.MyService;
import org.example.springcloud.entity.Friend;
import org.springframework.stereotype.Component;

/**
 * FallBack就是针对MyService的容错类
 */
@Slf4j
@Component
public class FallBack implements MyService {

    @Override
    @HystrixCommand(fallbackMethod = "fallback2")
    public String error() {
        log.error("进入容错类FallBack");
        throw new RuntimeException("first fallback");
    }

    @HystrixCommand(fallbackMethod = "fallback3")
    public String fallback2(){
        log.info("fallback again");
        throw new RuntimeException("fallback again");
    }

    public String fallback3(){
        log.info("fallback again and again");
        return "success";
    }

    @Override
    public String sayHi() {
        return null;
    }

    @Override
    public Friend sayHiPost(Friend friend) {
        return null;
    }

    @Override
    public String retry(int timeout) {
        log.error("进入容错类FallBack-retry");
        return "进入容错类FallBack-retry";
    }
}
