package com.lei.smart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@Slf4j
@EnableScheduling
public class JobStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobStartApplication.class, args);
    }

}
