package com.playground.springboot.common.pubsub.kafka;

import com.playground.springboot.common.pubsub.kafka.model.KafkaTopicsBean;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GenericKafkaConsumer {

    private final KafkaConsumer<Long, String> kafkaConsumer;

    @Autowired
    public GenericKafkaConsumer(KafkaConsumer<Long, String> kafkaConsumer, KafkaTopicsBean topics) {
        this.kafkaConsumer = kafkaConsumer;
        kafkaConsumer.subscribe(Collections.singletonList(topics.getInTopic()));
    }

    public KafkaConsumer<Long, String> getConsumer() {
        return kafkaConsumer;
    }

}
