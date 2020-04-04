package com.playground.springboot.translator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.playground.springboot.translator"})
public class TranslatorLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslatorLauncher.class);

    public static void main(String[] args) {
        SpringApplication.run(TranslatorLauncher.class, args);
    }

}
