package com.playground.springboot.common;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.Consumer;

public class ConsumerRunner implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerRunner.class);

    private final GenericKafkaConsumer kafkaConsumer;

    private String label;

    private Consumer<String> sendingFunction;

    public ConsumerRunner(GenericKafkaConsumer kafkaConsumer, String label, Consumer<String> sendingFunction) {
        this.kafkaConsumer = kafkaConsumer;
        this.label = label;
        this.sendingFunction = sendingFunction;
    }

    @Override
    public void run() {
        try {
            KafkaConsumer<Long, String> consumer = kafkaConsumer.getConsumer();
            while (true) {
                final ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(500));

                consumerRecords.forEach(record -> {
                    LOGGER.debug("Consumer Record:(%d, %s, %d, %d)\n",
                            record.key(), record.value(),
                            record.partition(), record.offset());
                    sendingFunction.accept(record.value() + " -"+label+"- ");
                });

                consumer.commitAsync();
            }
        } finally {
            kafkaConsumer.getConsumer().close();
        }
    }
}
