package com.playground.springboot.initiator;

import com.playground.springboot.common.GenericKafkaPublisher;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@RestController
public class InitiatorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InitiatorLauncher.class);

	private static final long SLEEP_DELAY = 500;

    private AtomicBoolean keepSending = new AtomicBoolean(false);

    private final ProducerRunner producerRunner;

    private static final Timer.Builder timer = Timer.builder("init_requests_latency_seconds")
            .publishPercentiles(0.5, 0.95, 0.99, 0.999)
            .publishPercentileHistogram()
            .sla(Duration.ofMillis(500))
            .minimumExpectedValue(Duration.ofMillis(1))
            .maximumExpectedValue(Duration.ofMillis(SLEEP_DELAY + 100));

	@Autowired
    public InitiatorController(GenericKafkaPublisher publisher, MeterRegistry registry) {
	    this.producerRunner = new ProducerRunner(publisher, timer.register(registry));
    }

    @GetMapping("/start")
    public void start(@RequestParam(value = "size", defaultValue = "100") int size) {
		LOGGER.info("Start method invoked with size " + size);
        if (keepSending.compareAndSet(false, true)) {
            producerRunner.setBatchSize(size);
            Thread runnerThread = new Thread(producerRunner);
			runnerThread.start();
        }
    }

    @GetMapping("/batch")
    public void setBatch(@RequestParam(value = "size", defaultValue = "100") int size) {
		LOGGER.info("SetBatch method invoked with size " + size);
        if (keepSending.get()) {
			producerRunner.setBatchSize(size);
        }
    }

    @GetMapping("/stop")
    public void stop() {
		LOGGER.info("Stop method invoked...");
        keepSending.compareAndSet(true, false);
    }

    private class ProducerRunner implements Runnable {

        private final GenericKafkaPublisher publisher;

        private final Timer timer;

        private long counter = 0;

        private AtomicInteger batchSize = new AtomicInteger(100);

        public ProducerRunner(GenericKafkaPublisher publisher, Timer timer) {
            this.publisher = publisher;
            this.timer = timer;
        }

        @Override
        public void run() {
            while (keepSending.get()) {
                IntStream.range(0, batchSize.get())
                        .forEach(i -> {
                            Instant start = Instant.now();
                            counter += i;
                            publisher.publish(counter + " -> Initiated - ");
                            Instant end = Instant.now();
                            timer.record(Duration.between(start, end));
                        });
				try {
					Thread.sleep(SLEEP_DELAY);
				} catch (InterruptedException e) {
					LOGGER.error(e.getMessage());
				}
			}
        }

        public void setBatchSize(int batchSize) {
            this.batchSize.getAndSet(batchSize);
        }
    }

}