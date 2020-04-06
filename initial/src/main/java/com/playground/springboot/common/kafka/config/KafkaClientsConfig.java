package com.playground.springboot.common.kafka.config;

import com.playground.springboot.common.kafka.model.KafkaTopicsBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
@EnableConfigurationProperties(KafkaClientConfigurationProperties.class)
public class KafkaClientsConfig {

    private final String inTopic;
    private final String outTopic;

    private final KafkaClientConfigurationProperties properties;

    public KafkaClientsConfig(KafkaClientConfigurationProperties properties) {
        this.properties = properties;
        this.inTopic = properties.getTopics().getInput();
        this.outTopic = properties.getTopics().getOutput();
    }

    @Bean
    @Primary
    public Properties getKafkaProperties() {
        Properties config = new Properties();
        config.put(CLIENT_ID_CONFIG, properties.getClientId());
        config.put(BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        config.put(KEY_SERIALIZER_CLASS_CONFIG, properties.getKeySerializer());
        config.put(VALUE_SERIALIZER_CLASS_CONFIG, properties.getValueSerializer());
        config.put(KEY_DESERIALIZER_CLASS_CONFIG, properties.getKeyDeserializer());
        config.put(VALUE_DESERIALIZER_CLASS_CONFIG, properties.getValueDeserializer());
        config.put("acks", properties.getAcks());
        config.put("group.id", properties.getConsumerGroupId());

        return config;
    }

    @Bean
    public KafkaTopicsBean getTopics() {
        return new KafkaTopicsBean(inTopic, outTopic);
    }

}