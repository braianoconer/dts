package com.playground.springboot.initiator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = {"com.playground.springboot.initiator", "com.playground.springboot.common.pubsub"})
public class InitiatorLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(InitiatorLauncher.class);

	public static void main(String[] args) {
		SpringApplication.run(InitiatorLauncher.class, args);
	}

	@Bean
    public CommandLineRunner entryCommandLineRunner(ApplicationContext ctx) {
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