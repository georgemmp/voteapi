package com.softbox.voteapi.application.useCase.schedule;

import com.softbox.voteapi.application.useCase.guideline.CloseSessions;
import com.softbox.voteapi.application.useCase.vote.CountVotes;
import com.softbox.voteapi.domain.port.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class SessionSchedule extends SessionScheduleAbstract{

    private final KafkaProducer kafkaProducer;

    public SessionSchedule(CloseSessions closeSessions,
                           CountVotes countVotes,
                           KafkaProducer kafkaProducer) {
        super(closeSessions, countVotes);
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    protected void sendMessage(Object object, String topic) {
        if (Objects.nonNull(object)) {
            log.info("Sending message to topic {}", topic);
            this.kafkaProducer.sendMessage(object, topic);
        }
    }
}
