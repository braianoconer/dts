package com.playground.observability.common.pubsub.kafka;

import com.playground.observability.common.pubsub.MsgPublisher;
import com.playground.observability.common.pubsub.kafka.model.KafkaTopicsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

@Service
public class SpringKafkaMsgPublisher implements MsgPublisher<Long, String> {

    private final KafkaTemplate<Long, String> template;

    private final KafkaTopicsBean topics;

    private long counter = 0;

    @Autowired
    public SpringKafkaMsgPublisher(ProducerFactory<Long, String> factory, KafkaTopicsBean topics) {
        template = new KafkaTemplate<>(factory);
        this.topics = topics;
    }

    @Override
    public Long publish(String msg) {
        long key = counter++;
        template.send(topics.getOutTopic(), key, msg);
        return key;
    }

    @Override
    public void close() throws Exception {
        //TODO
    }
}