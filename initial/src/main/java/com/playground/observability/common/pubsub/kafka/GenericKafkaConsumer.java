package com.playground.observability.common.pubsub.kafka;

import brave.kafka.clients.KafkaTracing;
import com.playground.observability.common.pubsub.kafka.model.KafkaTopicsBean;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GenericKafkaConsumer {

    private final Consumer<Long, String> kafkaConsumer;

    @Autowired
    public GenericKafkaConsumer(KafkaConsumer<Long, String> kafkaConsumer, KafkaTopicsBean topics, KafkaTracing tracing) {
        this.kafkaConsumer = tracing.consumer(kafkaConsumer);
        kafkaConsumer.subscribe(Collections.singletonList(topics.getInTopic()));
    }

    public Consumer<Long, String> getConsumer() {
        return kafkaConsumer;
    }

}
