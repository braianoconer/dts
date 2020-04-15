package com.playground.observability.italian;

import brave.http.HttpTracing;
import brave.spring.web.TracingClientHttpRequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.playground.observability.italian", "com.playground.observability.common"})
public class ItalianProcessingLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItalianProcessingLauncher.class);

    public static void main(String[] args) {
        SpringApplication.run(ItalianProcessingLauncher.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, HttpTracing tracing) {
        return builder
                .additionalInterceptors(TracingClientHttpRequestInterceptor.create(tracing))
                .build();
    }
}
