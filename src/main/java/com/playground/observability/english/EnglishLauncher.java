package com.playground.observability.english;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.playground.observability.english", "com.playground.observability.common.pubsub", "com.playground.observability.common.tracing"},
		exclude = KafkaAutoConfiguration.class)
public class EnglishLauncher {

	public static void main(String[] args) {
		SpringApplication.run(EnglishLauncher.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
}