package com.lei.smart;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//1.启动注册中心服务
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EurekaServerApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
