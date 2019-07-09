package com.lei.smart;

import com.lei.smart.util.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
@Slf4j
public class NoTranstationStartApplication {
    @Bean
    public SnowflakeIdWorker getSnow(){
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        return idWorker;
    }
    public static void main(String[] args) {
        SpringApplication.run(NoTranstationStartApplication.class, args);
    }

}
