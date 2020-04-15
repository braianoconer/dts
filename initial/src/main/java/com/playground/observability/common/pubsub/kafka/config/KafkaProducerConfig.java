package com.playground.observability.common.pubsub.kafka.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties({KafkaClientConfigurationProperties.class})
public class KafkaProducerConfig extends KafkaClientsConfig {

    public KafkaProducerConfig(KafkaClientConfigurationProperties properties) {
        super(properties);
    }

    @Override
    public Properties fillAdditionalProperties(Properties props) {
        return props;
    }
}
