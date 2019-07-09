package com.lei.smart.test;
import com.lei.smart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is demo test
 */
@RestController
public class DemoController {
    @Autowired
    UserService userService;
    @GetMapping("demo")
    public String getDemo() {
        userService.testMuti();
        return "ddd";
    }

}
