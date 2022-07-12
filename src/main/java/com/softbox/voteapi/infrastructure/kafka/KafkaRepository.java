package com.softbox.voteapi.infrastructure.kafka;

public interface KafkaRepository <T>{
    void producer(T t, String topic);
}
