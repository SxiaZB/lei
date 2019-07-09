package com.lei.smart.jobHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestConumer {
//    @Scheduled(cron = "* * * * * ?")
    public void consumer() {
    log.info("job>>>>");
    }
}
