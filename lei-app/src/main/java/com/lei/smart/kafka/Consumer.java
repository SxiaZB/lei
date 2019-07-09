//package com.lei.smart.kafka;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//@Component
//@Slf4j
//public class Consumer {
//
////    @KafkaListener(topics = {"TEST"})
////    public void listen(ConsumerRecord<?, ?> record){
////        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
////        if (kafkaMessage.isPresent()) {
////            Object message = kafkaMessage.get();
////            log.info("单条消息>>");
////           log.info("---->"+record);
////           log.info("---->"+message);
////        }
////    }
//    //批量消息
//    @KafkaListener(topics = {"TEST"},containerFactory="batchFactory")
//    public void consumerBatch(List<ConsumerRecord<?, ?>> record){
//        log.info("接收到消息数量：{}",record.size());
//        for (ConsumerRecord<?, ?> consumerRecord : record) {
//            Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
//            if (kafkaMessage.isPresent()) {
//                Object message = kafkaMessage.get();
//                log.info("批量中.....单条消息>>");
//                log.info("---->"+record);
//                log.info("---->"+message);
//            }
//        }
//    }
//    @Bean
//    public KafkaListenerContainerFactory<?> batchFactory(ConsumerFactory consumerFactory){
//        ConcurrentKafkaListenerContainerFactory<Integer,String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//        factory.setConcurrency(4);
//        factory.getContainerProperties().setPollTimeout(1500);
//        factory.setBatchListener(true);//设置为批量消费，每个批次数量在Kafka配置参数中设置
//        return factory;
//    }
//
//}
