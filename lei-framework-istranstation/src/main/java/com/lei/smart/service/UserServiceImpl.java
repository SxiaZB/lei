package com.lei.smart.service;

import com.lei.smart.mastermapper.UserMasterMapper;
import com.lei.smart.pojo.User;
import com.lei.smart.slavemapper.UserSlaveMapper;
import com.lei.smart.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserSlaveMapper userSlaveMapper;
    @Autowired
    private UserMasterMapper userMasterMapper;
@Autowired
private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    @Transactional(transactionManager = "xatx", propagation = Propagation.REQUIRED, rollbackFor = { java.lang.RuntimeException.class })
    public void testMuti() {
        User user =new User();
        user.setName("dmafds");
        user.setId(snowflakeIdWorker.nextId());
        user.setEmail("test mutyi");
        user.setAddress("你好地址");
        userMasterMapper.insert(user);
//        try {
//            Thread.sleep(3000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        userSlaveMapper.insert(user);
//        int a=2/0;
    }
}
