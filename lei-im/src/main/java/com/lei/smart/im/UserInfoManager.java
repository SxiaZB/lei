package com.lei.smart.im;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Channel的管理器
 *
 * @author dingjianlei
 */
public class UserInfoManager {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoManager.class);

    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);
    //      userid
    public static AttributeKey<String> USER_ID = AttributeKey.valueOf("userId");
    private static ConcurrentMap<String, Channel> ChatUsers = new ConcurrentHashMap<>();
    private static AtomicInteger userCount = new AtomicInteger(0);

    public static void addChannel(Channel channel, ChatUser chatUser) {

        channel.attr(USER_ID).set(String.valueOf(chatUser.getId()));
        ChatUsers.put(String.valueOf(chatUser.getId()), channel);
    }

    public static  Set<String> getAllUserId() {
        Set<String> userids = ChatUsers.keySet();
        return userids;
    }
    /**
     * 从缓存中移除Channel，并且关闭Channel
     *
     * @param channel
     */
    public static void removeChannel(Channel channel) {
        try {
            String user = channel.attr(USER_ID).get();
            logger.warn("channel will be remove, user is :{}", user);
            rwLock.writeLock().lock();
            String userId = channel.attr(UserInfoManager.USER_ID).get();
            channel.close();
            Channel channelCache = ChatUsers.get(userId);
            if (channelCache != null) {
                Channel remove = ChatUsers.remove(userId);
                if (remove != null) {
                    // 减去一个认证用户
                    userCount.decrementAndGet();
                }
            }
        } finally {
            rwLock.writeLock().unlock();
        }

    }





    public static void baseCastMessage(ChatMessage chatMessage, List<String> userIdList) {
        long startTime = System.currentTimeMillis();    //获取开始时间
        try {
            if(userIdList==null) return;
            for (String userId : userIdList) {
                Channel channel = ChatUsers.get(userId);
                if (channel != null && channel.isActive()) {
                    if (channel.isWritable()) {
                        channel.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(chatMessage))).addListener(future -> {
                            if (!future.isSuccess()) {
                                logger.warn("unexpected push. msg:{} fail:{}", JSONObject.toJSONString(chatMessage), future.cause().getMessage());
                            }
                        });
                    } else {
                        try {
                            channel.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(chatMessage))).sync();
                        } catch (InterruptedException e) {
                            logger.error("待发送数据量堆积超限", e);
                            e.printStackTrace();
                        }

                    }

                }
            }
            long endTime = System.currentTimeMillis();    //获取结束时间
            logger.info("发送耗时{}ms",endTime - startTime);
        } finally {
        }
    }

    /**
     * 扫描并关闭失效的Channel
     */
    public static void scanNotActiveChannel() {
        Set<String> keySet = ChatUsers.keySet();
        for (String userid : keySet) {
            Channel ch = ChatUsers.get(userid);
            if (!ch.isOpen() || !ch.isActive() || ch == null) {
                removeChannel(ch);
            }
        }
    }


}
