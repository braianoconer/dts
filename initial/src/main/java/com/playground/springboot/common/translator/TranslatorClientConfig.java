package com.playground.springboot.common.translator;

import com.playground.springboot.translator.TargetLanguage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TranslatorClientProperties.class)
public class TranslatorClientConfig {

    private final TranslatorClientProperties properties;

    public TranslatorClientConfig(TranslatorClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public String getTranslatorAddress() {
        return properties.getURL();
    }

    @Bean
    public TargetLanguage getTargetLanguage() {
        return properties.getTarget();
    }

}
