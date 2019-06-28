package com.lei.smart;

import com.lei.smart.mapper.UserMapper;
import com.lei.smart.mapper.WebchatSchoolMapper;
import com.lei.smart.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.peer.LabelPeer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WebchatServiceTests {
    @Autowired
    UserMapper userMapper;

    @Test
    public void contextLoads() {
        Config config = new Config();
        config.useSentinelServers().addSentinelAddress("172.22.31.82:26379", "172.22.31.82:26380",
                "172.22.31.82:26381")
                .setMasterName("mymaster");
//        CountDownLatch countDownLatch =new CountDownLatch(1);
        RedissonClient redissonClient = Redisson.create(config);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
        for (int i = 0; i < 100; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        log.info(Thread.currentThread().getName() + ">>>>> block");
                        cyclicBarrier.await();
                        log.info(Thread.currentThread().getName() + ">>>>> open");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    RLock lock = redissonClient.getLock("test10");
                    try {
                        boolean b = lock.tryLock(500, 5000, TimeUnit.MILLISECONDS);
                        log.info("获取到分布式锁<<<<<{}", b);
                        if (b) {
                            User userSelect = userMapper.selectByPrimaryKey(1l);
                            if (userSelect == null) {
                                log.info(Thread.currentThread().getName() + ">>>>> is  null");
                                User user = new User();
                                user.setId(1l);
                                user.setName("dingjainlei");
                                userMapper.insert(user);
                            } else {
                                log.info(Thread.currentThread().getName() + ">>>>> is not null");
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                        log.info("解锁");
                    }
                }
            });
        }
    }

}
