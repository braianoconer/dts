package com.playground.observability.common.pubsub.kafka;

import com.playground.observability.common.pubsub.MsgConsumer;
import com.playground.observability.common.pubsub.MsgPublisher;
import com.playground.observability.common.translator.TranslatorClientConfig;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public abstract class GenericProcessing {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericProcessing.class);

    private final Random rnd = new Random();
    protected final Timer timer;
    private final long maxDelayMillis;

    private final RestTemplate restTemplate;
    private final TranslatorClientConfig translatorClientConfig;
    private final MsgPublisher<Long, String> publisher;
    private final MsgConsumer<Long, String> consumer;


    public GenericProcessing(MsgPublisher<Long, String> publisher, MsgConsumer<Long, String> consumer,
                             RestTemplate restTemplate, TranslatorClientConfig translatorClientConfig,
                             long maxDelayMillis, Timer.Builder timer, MeterRegistry registry) {

        this.publisher = publisher;
        this.consumer = consumer;
        this.restTemplate = restTemplate;
        this.translatorClientConfig = translatorClientConfig;
        this.maxDelayMillis = maxDelayMillis;
        this.timer = timer.register(registry);
    }

    protected void send(String value)  {

        Instant start = Instant.now();

        try {
            Thread.sleep(Math.abs(rnd.nextLong()) % maxDelayMillis);
            publisher.publish(value);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        } finally {
            timer.record(Duration.between(start, Instant.now()));
        }
    }

    protected void startConsuming()  {
        LOGGER.info("startConsuming targetLanguage {}", translatorClientConfig.getTargetLanguage());
        consumer.start(this::consume);
    }

    private void consume(ConsumerRecord<Long, String> record)  {

        String value = record.value();
        String word = extractWord(value);

        String uri = translatorClientConfig.getTranslatorAddress() + "?target=" + translatorClientConfig.getTargetLanguage() + "&word=" + word;
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            send(value + response.getBody() + "-");
        } else {
            LOGGER.error("Received a translation error for word {}. Error: {}", value, response.getBody());
        }
    }

    private String extractWord(String input) {
        int beginIdx = input.indexOf(":") + 1;
        int endIdx = input.indexOf("-");
        return input.substring(beginIdx, endIdx);
    }
}
