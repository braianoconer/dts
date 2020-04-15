package com.playground.observability.common.pubsub.kafka;

import com.playground.observability.common.pubsub.Publisher;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public abstract class GenericProcessing {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericProcessing.class);

    private final Random rnd = new Random();

    protected final Timer timer;

    private final Publisher<Long, String> publisher;

    private final GenericKafkaConsumer kafkaConsumer;

    private final long maxDelayMillis;

    public GenericProcessing(Publisher<Long, String> publisher, GenericKafkaConsumer kafkaConsumer, long maxDelayMillis, Timer.Builder timer, MeterRegistry registry) {
        this.publisher = publisher;
        this.kafkaConsumer = kafkaConsumer;
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
            Instant end = Instant.now();
            timer.record(Duration.between(start, end));
        }
    }
}
