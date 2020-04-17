package com.playground.observability.common.pubsub.kafka;

import brave.kafka.clients.KafkaTracing;
import com.playground.observability.common.pubsub.Publisher;
import com.playground.observability.common.pubsub.kafka.model.KafkaTopicsBean;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericKafkaPublisher implements Publisher<Long, String> {

    private final Producer<Long, String> kafkaProducer;

    private final KafkaTopicsBean topics;

    private long counter = 0;

    @Autowired
    public GenericKafkaPublisher(KafkaProducer<Long, String> kafkaProducer, KafkaTopicsBean topics, KafkaTracing tracingProducer) {
        this.kafkaProducer = tracingProducer.producer(kafkaProducer);
        this.topics = topics;
    }

    @Override
    public Long publish(String name) {
        long key = counter++;
        final ProducerRecord<Long, String> record = new ProducerRecord<>(topics.getOutTopic(), key, name);
        kafkaProducer.send(record);
        return key;
    }

}