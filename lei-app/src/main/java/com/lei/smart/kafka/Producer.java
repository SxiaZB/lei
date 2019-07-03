package com.lei.smart.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.Date;
import java.util.UUID;

@Component
public class Producer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static Gson gson = new GsonBuilder().create();
    //发送消息方法
    @Async
    public void send(String msg) {
        Message message = new Message();
        message.setId("KFK_"+System.currentTimeMillis());
        message.setMsg(msg);
        message.setSendTime(new Date());
        ListenableFuture future = kafkaTemplate.send("TEST", gson.toJson(message));
//        SuccessCallback<? super T> successCallback = ;
//        future.addCallback(successCallback);
    }
}