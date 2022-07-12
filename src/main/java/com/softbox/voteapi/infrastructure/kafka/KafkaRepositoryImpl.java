package com.softbox.voteapi.infrastructure.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class KafkaRepositoryImpl<T> implements KafkaRepository<T>{
    @Autowired
    private StreamBridge streamBridge;

    @Override
    public void producer(T t, String topic) {
        this.streamBridge.send(topic, t);
    }
}
