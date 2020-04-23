package com.playground.observability.common.pubsub.kafka;

import com.playground.observability.common.pubsub.MsgConsumer;
import com.playground.observability.common.pubsub.kafka.model.KafkaTopicsBean;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Service
public class SpringKafkaMsgConsumer implements MsgConsumer<Long, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringKafkaMsgConsumer.class);

    private KafkaMessageListenerContainer<Long, String> container;

    private final ConsumerFactory<Long, String> factory;

    private final KafkaTopicsBean topics;

    private final AtomicBoolean isStarted = new AtomicBoolean(false);

    @Autowired
    public SpringKafkaMsgConsumer(ConsumerFactory<Long, String> factory, KafkaTopicsBean topics) {
        this.factory = factory;
        this.topics = topics;
    }

    @Override
    public void close() throws Exception {
        if (isStarted.compareAndSet(true, false)) {
            LOGGER.info("Stopping Kafka consumer for topic %s", topics.getInTopic());
            container.stop();
        }
    }

    @Override
    public void start(Consumer<ConsumerRecord<Long, String>> consumerFunction) {
        if (isStarted.compareAndSet(false, true)) {
            LOGGER.info("Creating Kafka consumer to consume messages from topic %s", topics.getInTopic());
            ContainerProperties containerProps = new ContainerProperties(topics.getInTopic());
            containerProps.setMessageListener(new MessageListener<Long, String>() {
                @Override
                public void onMessage(ConsumerRecord<Long, String> data) {
                    consumerFunction.accept(data);
                }
            });
            container = new KafkaMessageListenerContainer<>(factory, containerProps);
            LOGGER.info("Starting Kafka Message Listener with properties %d", containerProps.toString());
            container.start();
        }
    }
}