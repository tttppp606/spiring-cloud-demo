package org.example.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.swing.*;

@SpringBootApplication
@EnableDiscoveryClient//配置中心基于eureka的高可用改造
public class ConfigClientEurekaApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigClientEurekaApplication.class).
                web(WebApplicationType.SERVLET).
                run(args);
    }
}
