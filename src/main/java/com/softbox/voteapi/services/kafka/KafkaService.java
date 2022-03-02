package com.softbox.voteapi.services.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@Slf4j
public class KafkaService {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public ListenableFuture<SendResult<String, Object>> sendMessage(String topic, Object message) {
        log.info("Sending message to topic " + topic);
        return this.kafkaTemplate.send(topic, message);
    }
}
