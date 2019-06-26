package com.lei.smart.wxcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is demo test
 */
@RestController
public class DemoController {
    @GetMapping("demo")
    public String getDemo(){
        return "dingjianlei";
    }
}
