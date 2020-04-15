package com.playground.observability.common.pubsub.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class KafkaClientService {

    private final Properties kafkaProperties;

    @Autowired
    public KafkaClientService(Properties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public KafkaProducer<Long, String> kafkaProducer() {
        return new KafkaProducer<>(kafkaProperties);
    }

    @Bean
    public KafkaConsumer<Long, String> kafkaConsumer() {
        return new KafkaConsumer<>(kafkaProperties);
    }

}
