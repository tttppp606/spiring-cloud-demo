package springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
/**
 * @EnableFeignClients会扫描"org.example.*"下面所有被@FeignClient注解的类，
 * 也就会把公共模块feign-client-intf里的IService接口扫描到
 */
@EnableFeignClients("org.example.*")/* 为了调用feign接库，注入feign相关组件*/
public class FeignConsumerAdvancedApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(FeignConsumerAdvancedApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
