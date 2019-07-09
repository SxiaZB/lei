package com.lei.smart.test;
import com.google.gson.Gson;
import com.lei.smart.mapper.UserMapper;
import com.lei.smart.pojo.User;
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
    @Autowired
    private UserMapper userMapper;
    @GetMapping("demo")
    public String getDemo() {
        userService.testMuti();
        return "ddd";
    }
    @GetMapping("getOne")
    public String getOne() {
        Gson gson=new Gson();
        User user = userMapper.selectByPrimaryKey(1l);
        return gson.toJson(user);
    }
}
