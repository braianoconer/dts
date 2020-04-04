package com.playground.springboot.common.kafka;

import com.playground.springboot.common.kafka.model.KafkaTopicsBean;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericKafkaPublisher {

    private final Producer<Long, String> kafkaProducer;

    private final KafkaTopicsBean topics;

    private long counter = 0;

    @Autowired
    public GenericKafkaPublisher(KafkaProducer<Long, String> kafkaProducer, KafkaTopicsBean topics) {
        this.kafkaProducer = kafkaProducer;
        this.topics = topics;
    }

    public long publish(String name) {
        long key = counter++;
        final ProducerRecord<Long, String> record = new ProducerRecord<>(topics.getOutTopic(), key, name);
        kafkaProducer.send(record);
        return key;
    }

}