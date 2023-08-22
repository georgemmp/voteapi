package com.softbox.voteapi.domain.port;

public interface KafkaProducer<T>{
    void sendMessage(T t, String topic);
}
