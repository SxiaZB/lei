package com.lei.smart.service;

import com.google.gson.Gson;
import com.lei.smart.mapper.UserMapper;
import com.lei.smart.pojo.User;
import com.lei.smart.util.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    @Transactional
    public void testMuti() {
        User user = new User();
        user.setName("dmafds");
        user.setId(snowflakeIdWorker.nextId());
        user.setEmail("test mutyi");
        user.setAddress("你好地址");
        userMapper.insert(user);
//        int a=9/0;
        User user1 = userMapper.selectByPrimaryKey(1l);
        log.info(new Gson().toJson(user1));
        int a = 9 / 0;
    }
}
