package com.lei.smart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ServletComponentScan
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true)

public class SsoStartApplication {


    public static void main(String[] args) {
        SpringApplication.run(SsoStartApplication.class, args);
    }

}
