package com.playground.springboot.heavy;

import com.playground.springboot.common.ConsumerRunner;
import com.playground.springboot.common.GenericKafkaConsumer;
import com.playground.springboot.common.GenericKafkaPublisher;
import com.playground.springboot.common.GenericProcessing;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class HeavyProcessingController extends GenericProcessing {

    private static final long MAX_DELAY_MILLIS = 6000;

    private static final String LABEL = "HEAVY";

    private static final Timer.Builder timer = Timer.builder("heavy_requests_latency_seconds")
            .publishPercentiles(0.5, 0.95, 0.99, 0.999)
            .publishPercentileHistogram()
            .sla(Duration.ofMillis(MAX_DELAY_MILLIS))
            .minimumExpectedValue(Duration.ofMillis(1))
            .maximumExpectedValue(Duration.ofMillis(MAX_DELAY_MILLIS + 100));

    @Autowired
    public HeavyProcessingController(GenericKafkaPublisher kafkaPublisher, GenericKafkaConsumer kafkaConsumer, MeterRegistry registry) {
        super(kafkaPublisher, kafkaConsumer, MAX_DELAY_MILLIS, timer, registry);
        Thread consumerThread = new Thread(new ConsumerRunner(kafkaConsumer, LABEL, this::send));
        consumerThread.start();
    }

    @GetMapping("/publish")
    public String publish(@RequestParam(value = "name", defaultValue = LABEL) String name) {
        send(name);
        return "Message sent to the topic!";
    }

}