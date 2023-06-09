package org.example.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringCloudApplication
public class HystrixDashBoardApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(HystrixDashBoardApplication.class).
                web(WebApplicationType.SERVLET).
                run(args);
    }
}
