package com.playground.observability.english;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.playground.observability.english", "com.playground.observability.common.pubsub", "com.playground.observability.common.tracing"})
public class EnglishLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnglishLauncher.class);

	public static void main(String[] args) {
		SpringApplication.run(EnglishLauncher.class, args);
	}
	
}