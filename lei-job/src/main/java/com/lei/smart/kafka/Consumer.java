package com.lei.smart.kafka;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lei.smart.es.EsUser;
import com.lei.smart.es.EsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class Consumer {
    @Autowired
    private EsUtil util;

    //    @KafkaListener(topics = {"TEST"})
//    public void listen(ConsumerRecord<?, ?> record){
//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//        if (kafkaMessage.isPresent()) {
//            Object message = kafkaMessage.get();
//            log.info("单条消息>>");
//           log.info("---->"+record);
//           log.info("---->"+message);
//        }
//    }
    //批量消息
    @KafkaListener(topics = {"TEST"}, containerFactory = "batchFactory")
    public void consumerBatch(List<ConsumerRecord<?, ?>> record) {
        log.info("接收到消息数量：{}", record.size());
        for (ConsumerRecord<?, ?> consumerRecord : record) {
            Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
            if (kafkaMessage.isPresent()) {
                String message = (String) kafkaMessage.get();
                log.info("批量中.....单条消息>>");
                log.info("消息message---->" + message);
                String index = "mini";
                String type = "man";
                EsUser esUser=new EsUser();
                esUser.setName(message);
                esUser.setDate(System.currentTimeMillis());
                esUser.setCountry(UUID.randomUUID().toString());
                esUser.setAge("12");
                try {
                    util.addDocToIndex(index,type,esUser);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchFactory(ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(4);
        factory.getContainerProperties().setPollTimeout(1500);
        factory.setBatchListener(true);//设置为批量消费，每个批次数量在Kafka配置参数中设置
        return factory;
    }

}
