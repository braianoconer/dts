package com.playground.observability.common.pubsub.kafka;

import com.playground.observability.common.translator.TranslatorClientConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.function.Consumer;

public class ConsumerRunner implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerRunner.class);

    private final GenericKafkaConsumer kafkaConsumer;

    private Consumer<String> sendingFunction;

    private final TranslatorClientConfig translatorClientConfig;

    private final RestTemplate restTemplate;

    public ConsumerRunner(GenericKafkaConsumer kafkaConsumer,
                          Consumer<String> sendingFunction,
                          TranslatorClientConfig translatorClientConfig,
                          RestTemplate restTemplate) {
        this.kafkaConsumer = kafkaConsumer;
        this.sendingFunction = sendingFunction;
        this.translatorClientConfig = translatorClientConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run() {
        try {
            org.apache.kafka.clients.consumer.Consumer<Long, String> consumer = kafkaConsumer.getConsumer();
            while (true) {
                final ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(500));

                consumerRecords.forEach(record -> {
                    String value = record.value();
                    String word = extractWord(value);
                    String uri = translatorClientConfig.getTranslatorAddress() + "?target=" + translatorClientConfig.getTargetLanguage() + "&word=" + word;
                    String translation =restTemplate.getForObject(uri, String.class);
                    sendingFunction.accept(value + translation + "-");
                });

                consumer.commitAsync();
            }
        } finally {
            kafkaConsumer.getConsumer().close();
        }
    }

    private String extractWord(String input) {
        int beginIdx = input.indexOf(":") + 1;
        int endIdx = input.indexOf("-");
        return input.substring(beginIdx, endIdx);
    }
}
