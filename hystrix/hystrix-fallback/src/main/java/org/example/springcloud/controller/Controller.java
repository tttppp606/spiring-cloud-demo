package org.example.springcloud.controller;

import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.Cleanup;
import org.example.springcloud.MyService;
import org.example.springcloud.RequestCacheService;
import org.example.springcloud.entity.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private MyService myService;

    @Autowired
    private RequestCacheService requestCacheService;

    /**
     * 多级降级
     * @return
     */
    @GetMapping("/fallback")
    public String fallBack(){
        return myService.error();
    }

    /**
     * 超时降级-配置文件
     * @param timeout
     * @return
     */
    @GetMapping("/timeout")
    public String timeout(@RequestParam int timeout) {
        return myService.retry(timeout);
    }

    /**
     * 超时降级-代码的形式实现配置
     *
     * commandProperties是一个数组，可以继续指定更多的参数
     * @param timeout
     * @return
     */
    @GetMapping("/timeout2")
    @HystrixCommand(
            fallbackMethod = "timeout2FallBack",
            commandProperties = {
                      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                                       value = "60000")
    })
    public String timeout2(@RequestParam int timeout) {
        return myService.retry(timeout);
    }

    public String timeout2FallBack(int timeout){
        return "success";
    }



    @GetMapping("sayHi")
    public String sayHi(){
        return myService.sayHi();
    }

    /**
     * requestCache用法
     * @param name
     * @return
     */
    @GetMapping("/cache")
    public Friend cache(String name){
        @Cleanup
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Friend friend = requestCacheService.requestCache(name);
        //name += "!";
        friend = requestCacheService.requestCache(name);
        return friend;
    }
}
