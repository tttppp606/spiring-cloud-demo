package org.example.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaPeer2Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaPeer2Application.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
