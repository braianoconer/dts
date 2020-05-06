package com.playground.observability.english;

import brave.Tracer;
import com.playground.observability.common.InputWords;
import com.playground.observability.common.pubsub.MsgPublisher;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@RestController
public class EnglishController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnglishLauncher.class);

	private static final long SLEEP_DELAY = 500;

    private AtomicBoolean keepSending = new AtomicBoolean(false);

    private final Random rnd = new Random();

    private final ProducerRunner producerRunner;

    private final RestTemplate restTemplate;

    @Autowired
    private Tracer tracer;

    private static final Timer.Builder timer = Timer.builder("english_requests_latency_seconds")
            .publishPercentiles(0.5, 0.95, 0.99, 0.999)
            .publishPercentileHistogram()
            .sla(Duration.ofMillis(500))
            .minimumExpectedValue(Duration.ofMillis(1))
            .maximumExpectedValue(Duration.ofMillis(SLEEP_DELAY + 100));

    public EnglishController(MsgPublisher<Long, String> publisher, MeterRegistry registry, RestTemplate restTemplate) {
	    this.producerRunner = new ProducerRunner(publisher, timer.register(registry));
	    this.restTemplate = restTemplate;
    }

    @GetMapping("/test")
    public void test() {

        LOGGER.warn("---------");
        printTrace();
        LOGGER.warn("---------");

        String uri = "http://translator:8085/translate?target=ES&word=hello";
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("OK {}", response.getBody());
        }
    }

    private void printTrace () {
        String traceId = tracer.currentSpan().context().traceIdString();
        String spanId = tracer.currentSpan().context().spanIdString();
        LOGGER.info("--- Tracer ---  traceId: {} & spanId: {}", traceId, spanId);
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

        private final MsgPublisher<Long, String> publisher;

        private final Timer timer;

        private long counter = 0;

        private AtomicInteger batchSize = new AtomicInteger(100);

        private final List<String> inputs = InputWords.getEnglishWords();

        public ProducerRunner(MsgPublisher<Long, String> publisher, Timer timer) {
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
                            publisher.publish(counter + ":" + getRandomInput() + "-");
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

        private String getRandomInput() {
            return inputs.get(Math.abs(rnd.nextInt()) % (inputs.size() - 1));
        }
    }

}