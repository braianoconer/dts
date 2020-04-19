package com.playground.observability.common.pubsub.kafka;

import com.playground.observability.common.pubsub.MsgConsumer;
import com.playground.observability.common.translator.TranslatorClientConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
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
            String value = record.value();
            String word = extractWord(value);
            String uri = translatorClientConfig.getTranslatorAddress() + "?target=" + translatorClientConfig.getTargetLanguage() + "&word=" + word;
            String translation =restTemplate.getForObject(uri, String.class);
            sendingFunction.accept(value + translation + "-");
        });

    }

    private String extractWord(String input) {
        int beginIdx = input.indexOf(":") + 1;
        int endIdx = input.indexOf("-");
        return input.substring(beginIdx, endIdx);
    }

}
