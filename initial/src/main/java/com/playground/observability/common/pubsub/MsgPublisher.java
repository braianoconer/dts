package com.playground.observability.common.pubsub;

public interface MsgPublisher<K, V> extends AutoCloseable {

    public Long publish(V msg);
}
