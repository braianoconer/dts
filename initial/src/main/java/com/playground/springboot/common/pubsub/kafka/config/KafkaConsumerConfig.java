package com.playground.springboot.common.pubsub.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties({KafkaClientConfigurationProperties.class})
public class KafkaConsumerConfig extends KafkaClientsConfig {

    public KafkaConsumerConfig(KafkaClientConfigurationProperties properties) {
        super(properties);
    }

    @Override
    public Properties fillAdditionalProperties(Properties props) {
        props.put("group.id", properties.getConsumerGroupId());
        return props;
    }
}