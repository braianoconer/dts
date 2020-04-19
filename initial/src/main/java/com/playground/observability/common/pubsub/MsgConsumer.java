package com.playground.observability.common.pubsub;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.function.Consumer;

public interface MsgConsumer<K, V> extends AutoCloseable {

    void start(Consumer<ConsumerRecord<Long, String>> consumerFunction);

}
