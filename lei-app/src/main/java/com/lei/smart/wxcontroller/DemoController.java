package com.lei.smart.wxcontroller;

import com.lei.smart.kafka.Producer;
import com.lei.smart.mapper.WebchatSchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is demo test
 */
@RestController
public class DemoController {
    @Autowired
    private Producer producer;
    @GetMapping("demo")
    public String getDemo() {
        return "app-remote-dingjianlei";
    }
    @GetMapping("send")
    public String send(String message) {
        for (int i = 0; i < 50; i++) {
            producer.send(i+"");
        }
        return "dd";
    }
}
