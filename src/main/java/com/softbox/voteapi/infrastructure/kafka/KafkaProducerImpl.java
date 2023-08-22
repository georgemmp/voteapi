package com.softbox.voteapi.infrastructure.kafka;

import com.softbox.voteapi.domain.port.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerImpl<T> implements KafkaProducer<T> {
    @Autowired
    private StreamBridge streamBridge;

    @Override
    public void sendMessage(T t, String topic) {
        this.streamBridge.send(topic, t);
    }
}
