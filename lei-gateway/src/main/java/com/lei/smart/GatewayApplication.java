package com.lei.smart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 〈服务网关〉
 */
@SpringBootApplication  //Boot应用
@EnableEurekaClient //Eureka客户端注册
@EnableDiscoveryClient  //服务发现
@EnableZuulProxy    //服务网关
public class GatewayApplication {

    public static void main(String[] args) {
            SpringApplication.run(GatewayApplication.class, args);
    }

}