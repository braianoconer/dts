package com.playground.springboot.light;

import com.playground.springboot.common.kafka.ConsumerRunner;
import com.playground.springboot.common.kafka.GenericKafkaConsumer;
import com.playground.springboot.common.kafka.GenericKafkaPublisher;
import com.playground.springboot.common.kafka.GenericProcessing;
import com.playground.springboot.common.translator.TranslatorClientConfig;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@RestController
public class LightProcessingController extends GenericProcessing {

    private static final long MAX_DELAY_MILLIS = 500;

    private final RestTemplate restTemplate;

    private final TranslatorClientConfig translatorClientConfig;

    private static final Timer.Builder timer = Timer.builder("light_requests_latency_seconds")
            .publishPercentiles(0.5, 0.95, 0.99, 0.999)
            .publishPercentileHistogram()
            .sla(Duration.ofMillis(MAX_DELAY_MILLIS))
            .minimumExpectedValue(Duration.ofMillis(1))
            .maximumExpectedValue(Duration.ofMillis(MAX_DELAY_MILLIS + 100));

    @Autowired
    public LightProcessingController(GenericKafkaPublisher kafkaPublisher,
                                     GenericKafkaConsumer kafkaConsumer,
                                     MeterRegistry registry,
                                     TranslatorClientConfig translatorClientConfig,
                                     RestTemplate restTemplate) {
        super(kafkaPublisher, kafkaConsumer, MAX_DELAY_MILLIS, timer, registry);
        this.restTemplate = restTemplate;
        this.translatorClientConfig = translatorClientConfig;
        Thread consumerThread = new Thread(new ConsumerRunner(kafkaConsumer, this::send, translatorClientConfig, restTemplate));
        consumerThread.start();
    }

}