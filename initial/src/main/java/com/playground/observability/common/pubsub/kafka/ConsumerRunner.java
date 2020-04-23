package com.playground.observability.common.pubsub.kafka;

import brave.Response;
import com.playground.observability.common.pubsub.MsgConsumer;
import com.playground.observability.common.translator.TranslatorClientConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

public class ConsumerRunner implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerRunner.class);

    private final MsgConsumer<Long, String> msgConsumer;

    private Consumer<String> sendingFunction;

    private final TranslatorClientConfig translatorClientConfig;

    private final RestTemplate restTemplate;

    public ConsumerRunner(MsgConsumer<Long, String> msgConsumer,
                          Consumer<String> sendingFunction,
                          TranslatorClientConfig translatorClientConfig,
                          RestTemplate restTemplate) {
        this.msgConsumer = msgConsumer;
        this.sendingFunction = sendingFunction;
        this.translatorClientConfig = translatorClientConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run() {
        msgConsumer.start(record -> {
            Instant start = Instant.now();
            String value = record.value();
            String word = extractWord(value);
            String uri = translatorClientConfig.getTranslatorAddress() + "?target=" + translatorClientConfig.getTargetLanguage() + "&word=" + word;
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                sendingFunction.accept(value + response.getBody() + "-");
            } else {
                LOGGER.error("Received a translation error for word {}. Error: {}", value, response.getBody());
            }

        });

    }

    private String extractWord(String input) {
        int beginIdx = input.indexOf(":") + 1;
        int endIdx = input.indexOf("-");
        return input.substring(beginIdx, endIdx);
    }

}
