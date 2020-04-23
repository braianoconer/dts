package com.playground.observability.common.pubsub.kafka;

import com.playground.observability.common.pubsub.MsgConsumer;
import com.playground.observability.common.pubsub.kafka.model.KafkaTopicsBean;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

//@Service
public class GenericKafkaConsumer implements MsgConsumer<Long, String> {

    private final KafkaConsumer<Long, String> kafkaConsumer;

    private final AtomicBoolean isStarted = new AtomicBoolean(false);

    @Autowired
    public GenericKafkaConsumer(KafkaConsumer<Long, String> kafkaConsumer, KafkaTopicsBean topics) {
        this.kafkaConsumer = kafkaConsumer;
        kafkaConsumer.subscribe(Collections.singletonList(topics.getInTopic()));
    }

    public KafkaConsumer<Long, String> getConsumer() {
        return kafkaConsumer;
    }

    @Override
    public void close() throws Exception {
        isStarted.compareAndSet(true, false);
        kafkaConsumer.close();
    }

    @Override
    public void start(Consumer<ConsumerRecord<Long, String>> consumerFunction) {
        while (isStarted.compareAndSet(false, true)) {
            final ConsumerRecords<Long, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(500));
            consumerRecords.forEach(consumerFunction);
            kafkaConsumer.commitAsync();
        }
    }
}
