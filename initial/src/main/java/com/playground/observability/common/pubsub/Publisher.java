package com.playground.observability.common.pubsub;

public interface Publisher<K, V> {

    public Long publish(V msg);
}
