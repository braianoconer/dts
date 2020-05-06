package com.playground.observability.spanish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication(scanBasePackages = {"com.playground.observability.spanish", "com.playground.observability.common"},
        exclude = KafkaAutoConfiguration.class)
public class SpanishProcessingLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpanishProcessingLauncher.class);

    public static void main(String[] args) {
        SpringApplication.run(SpanishProcessingLauncher.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        LOGGER.info("Creating RestTemplate instance for Spanish translations...");
        return builder.build();
    }
}
