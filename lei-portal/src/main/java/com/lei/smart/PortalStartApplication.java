package com.lei.smart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients //开启Feign客户端代理功能
public class PortalStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortalStartApplication.class, args);
    }

}
