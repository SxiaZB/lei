package com.lei.smart.controller;

import com.lei.smart.remote.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is demo test
 */
@RestController
public class DemoController {
    @Autowired
    private RestService restService;
    @GetMapping("demo")
    public String getDemo() {
        return restService.getRemote();
    }
    @GetMapping("demo2")
    public String getDemo2() {
        return "demo2";
    }
}
