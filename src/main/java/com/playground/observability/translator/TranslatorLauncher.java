package com.playground.observability.translator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.playground.observability.translator"})
public class TranslatorLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslatorLauncher.class);

    public static void main(String[] args) {
        SpringApplication.run(TranslatorLauncher.class, args);
    }

}
