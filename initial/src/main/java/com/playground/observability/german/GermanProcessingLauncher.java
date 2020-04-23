package com.playground.observability.german;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.playground.observability.german", "com.playground.observability.common"},
        exclude = KafkaAutoConfiguration.class)
public class GermanProcessingLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(GermanProcessingLauncher.class);

    public static void main(String[] args) {
        SpringApplication.run(GermanProcessingLauncher.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        LOGGER.info("Creating RestTemplate instance for German translations...");
        return builder.build();
    }
}
