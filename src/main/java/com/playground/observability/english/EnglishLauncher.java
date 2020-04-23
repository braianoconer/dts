package com.playground.observability.english;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.playground.observability.english", "com.playground.observability.common.pubsub", "com.playground.observability.common.tracing"},
		exclude = KafkaAutoConfiguration.class)
public class EnglishLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnglishLauncher.class);

	public static void main(String[] args) {
		SpringApplication.run(EnglishLauncher.class, args);
	}
	
}