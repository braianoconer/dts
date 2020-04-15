package com.playground.observability.spanish;

import brave.http.HttpTracing;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = {"com.playground.observability.spanish", "com.playground.observability.common"})
public class SpanishProcessingLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpanishProcessingLauncher.class);

    public static void main(String[] args) {
        SpringApplication.run(SpanishProcessingLauncher.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, HttpTracing tracing) {
        return builder
                .additionalInterceptors(TracingClientHttpRequestInterceptor.create(tracing))
                .build();
    }
}
