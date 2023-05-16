package org.example.springcloud;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class RibbonConsumerApplication {
    /**
     * 作用：封装http请求模版，使其与Ribbon结合
     * @return
     */
    @Bean
    @LoadBalanced/*  该注解可以将负载均衡信息直接传给restTemplate */
    public RestTemplate template(){
        return new RestTemplate();
    }

//    /**
//     * 作用：用于改变Ribbon的负载均衡策略，默认为rr
//     * 原理：返回的IRule将会替换默认的IRule
//     * @return
//     */
//    @Bean
//    public IRule defaultLBStrategy(){
//        return new RandomRule();
//    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(RibbonConsumerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
