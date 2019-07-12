package com.lei.smart.wxcontroller;

import com.lei.smart.UserService;
import com.lei.smart.ResponseResultVO;
import com.lei.smart.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is demo test
 */
@RestController
public class IndexController {
    @Autowired
    private Producer producer;
    @Autowired
    private UserService userService;

    @GetMapping("demo")
    public ResponseResultVO getDemo() {
        return userService.testException();
    }

    @GetMapping("insertUser")
    public String insertUser() {
        userService.insert();
        return "success";
    }


    @GetMapping("send")
    public String send(String message) {
        producer.send(message);
        return message;
    }
}
