package com.playground.observability.german;

import brave.http.HttpTracing;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.playground.observability.german", "com.playground.observability.common"})
public class GermanProcessingLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(GermanProcessingLauncher.class);

    public static void main(String[] args) {
        SpringApplication.run(GermanProcessingLauncher.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, HttpTracing tracing) {
        LOGGER.info("Creating RestTemplate instance with tracing directed to %s", tracing.serverName());
        return builder
                .additionalInterceptors(TracingClientHttpRequestInterceptor.create(tracing))
                .build();
    }
}
