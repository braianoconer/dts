package com.playground.springboot.common.config;

import com.playground.springboot.common.model.KafkaTopicsBean;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.UnknownHostException;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class KafkaClientsConfig {

    private final String inTopic = "in";
    private final String outTopic = "out";

    @Bean
    @Primary
    public Properties getKafkaProperties() throws UnknownHostException {
        Properties config = new Properties();
        config.put(CLIENT_ID_CONFIG, "ObservClient");
        config.put(BOOTSTRAP_SERVERS_CONFIG, "192.168.191.190:9092");
        config.put(KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        config.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        config.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put("acks", "all");
        config.put("group.id", "foo");

        return config;
    }

    @Bean
    public KafkaTopicsBean getTopics() {
        return new KafkaTopicsBean(inTopic, outTopic);
    }

}