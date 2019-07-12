package com.lei.smart;

import com.google.common.base.Preconditions;
import com.lei.smart.mapper.UserMapper;
import com.lei.smart.pojo.User;
import com.lei.smart.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public void insert() {
        User user = new User();
        user.setId(snowflakeIdWorker.nextId());
        user.setName("ddd");
        user.setAddress("dd");
        user.setEmail("ddfadg");
        userMapper.insert(user);
    }

    @Override
    public ResponseResultVO testException() {
        LeiPreconditions.checkNotNull("dd", "neme为null");
        return new ResponseResultUtil().error(ResponseCodeEnum.OPERATE_FAIL);
    }
}
