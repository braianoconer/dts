package com.playground.observability.italian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.playground.observability.italian", "com.playground.observability.common"},
        exclude = KafkaAutoConfiguration.class)
public class ItalianProcessingLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItalianProcessingLauncher.class);

    public static void main(String[] args) {
        SpringApplication.run(ItalianProcessingLauncher.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        LOGGER.info("Creating RestTemplate instance for Italian translations...");
        return builder.build();
    }

}
