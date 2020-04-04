package com.playground.springboot.common.kafka.model;

public class KafkaTopicsBean {

    private final String inTopic;

    private final String outTopic;

    public KafkaTopicsBean(String inTopic, String outTopic) {
        this.inTopic = inTopic;
        this.outTopic = outTopic;
    }

    public String getInTopic() {
        return inTopic;
    }

    public String getOutTopic() {
        return outTopic;
    }

    @Override
    public String toString() {
        return "KafkaTopicsBean{" +
                "inTopic='" + inTopic + '\'' +
                ", outTopic='" + outTopic + '\'' +
                '}';
    }
}
