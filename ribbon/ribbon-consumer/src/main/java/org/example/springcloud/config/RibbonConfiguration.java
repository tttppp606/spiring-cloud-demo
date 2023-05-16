package org.example.springcloud.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * 该注解也可以指定负载均衡策略
 */
@RibbonClient(name = "eureka-client",configuration = com.netflix.loadbalancer.RandomRule.class)
public class RibbonConfiguration {
//    /**
//     * 作用：用于改变Ribbon的负载均衡策略，默认为rr
//     * 原理：返回的IRule将会替换默认的IRule
//     * @return
//     */
//    @Bean
//    public IRule defaultLBStrategy(){
//        return new RandomRule();
//    }
}
