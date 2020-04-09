package com.playground.springboot.medium;

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

@SpringBootApplication(scanBasePackages = {"com.playground.springboot.medium", "com.playground.springboot.common"})
public class MediumProcessingLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MediumProcessingLauncher.class);

    public static void main(String[] args) {
        SpringApplication.run(MediumProcessingLauncher.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner lightProcessingCommandLineRunner(ApplicationContext ctx) {
        return args -> {
            LOGGER.debug("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                LOGGER.debug(beanName);
            }
        };
    }
}