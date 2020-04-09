package com.playground.springboot.common.pubsub;

public interface Publisher<K, V> {

    public Long publish(V msg);
}
