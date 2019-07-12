package com.lei.smart.IndexController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is demo test
 */
@RestController
public class DemoController {

    @GetMapping("demo")
    public String getDemo() {
        return "app-remote-dingjianlei";
    }
}
