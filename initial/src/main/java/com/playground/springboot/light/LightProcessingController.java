package com.playground.springboot.light;

import com.playground.springboot.common.model.KafkaTopicsBean;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
public class LightProcessingController {

    private final KafkaProducer<Long, String> kafkaProducer;

    private final KafkaTopicsBean topics;

    private long counter = 0;

    @Autowired
    public LightProcessingController(KafkaProducer<Long, String> kafkaProducer, KafkaTopicsBean topics) {
        this.kafkaProducer = kafkaProducer;
        this.topics = topics;
    }

    @RequestMapping("/")
    public String index2() {
        return "Bye bye Miss American Pie!";
    }

    @GetMapping("/publish")
    public String publish(@RequestParam(value = "name", defaultValue = "pepe") String name) {
        long key = counter++;
        final ProducerRecord<Long, String> record = new ProducerRecord<>(topics.getInTopic(), key, name);
        kafkaProducer.send(record);
        return "Message with key" + key + " sent to the topic!";
    }

}
