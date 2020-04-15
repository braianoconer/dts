package com.playground.observability.german;

import com.playground.observability.common.pubsub.kafka.ConsumerRunner;
import com.playground.observability.common.pubsub.kafka.GenericKafkaConsumer;
import com.playground.observability.common.pubsub.kafka.GenericKafkaPublisher;
import com.playground.observability.common.pubsub.kafka.GenericProcessing;
import com.playground.observability.common.translator.TranslatorClientConfig;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@RestController
public class GermanProcessingController extends GenericProcessing {

    private static final long MAX_DELAY_MILLIS = 6000;

    private static final Timer.Builder timer = Timer.builder("german_requests_latency_seconds")
            .publishPercentiles(0.5, 0.95, 0.99, 0.999)
            .publishPercentileHistogram()
            .sla(Duration.ofMillis(MAX_DELAY_MILLIS))
            .minimumExpectedValue(Duration.ofMillis(1))
            .maximumExpectedValue(Duration.ofMillis(MAX_DELAY_MILLIS + 100));

    @Autowired
    public GermanProcessingController(GenericKafkaPublisher kafkaPublisher,
                                      GenericKafkaConsumer kafkaConsumer,
                                      MeterRegistry registry,
                                      TranslatorClientConfig translatorClientConfig,
                                      RestTemplate restTemplate) {
        super(kafkaPublisher, kafkaConsumer, MAX_DELAY_MILLIS, timer, registry);
        Thread consumerThread = new Thread(new ConsumerRunner(kafkaConsumer, this::send, translatorClientConfig, restTemplate));
        consumerThread.start();
    }

}