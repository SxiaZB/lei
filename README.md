# 欢迎使用 lei开源springcloud脚手架

------

为了更快进行开发，搭建了一套适合后台的脚手架lei，前后端分离，restful风格接口开发，适合二次开发，主要包含以下模块。

> * 常见cloud模块  全家桶 注册中心 熔断 降级 网关等等
> * 集成常见java后台框架 mongodb  redis  mq zookeeper注册发现等等
> * 基于netty的websocket拓展
> * sso单点登录
> * 常见功能 限流 分布式锁的实现等
> * 分库分表核心jar包实现 以及分库产生的跨库事物等
> * 单机分布式事物框架整合 atomikos处理多数据源分布式事物
> * 还有其他功能待整合。

------

# 以下一些思路分享

# ** 基于redission分布式锁demo  模拟100线程同时插入

```
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

        config.useSentinelServers().addSentinelAddress("****,*****,****")
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
//                        cyclicBarrier.
                        log.info(Thread.currentThread().getName() + ">>>>> block");
                        cyclicBarrier.await();
                        log.info(Thread.currentThread().getName() + ">>>>> open");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
//                    log.info("加锁前");
                    RLock lock = redissonClient.getLock("test10");
//                    log.info("加锁后");
                    try {
//                        log.info("尝试获取到分布式锁<<<<<");
                        boolean b = lock.tryLock(500, 2000, TimeUnit.MILLISECONDS);
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
//                                redissonClient.shutdown();
                    }
                }
            });
        }


    }

}

```

### 常规操作
一般是先去数据库查询，如果不存在，则进行插入。如果存在，则不进行操作



### 模拟思路

```
使用CyclicBarrier 100个线程同时等待await=0的时候同时并发请求查询数据库id=1存不存在
redission 使用tryLock 尝试获取锁 设置超时 自动释放锁时间  防止死锁 ，保证分布式环境下 高并发插入重复问题
```
### End






## 如何启动

下载到本地之后，在msyql中创建数据库lei，一次启动即可

## 问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

邮件(m13687672481@163.com)

QQ: 1251272104

微信: kenan13687672481


## 关于作者

下载到本地之后，在msyql中创建数据库lei，一次启动即可

  var author = {
  
    nickName  : "工藤新一",
    
  }
