package com.softbox.voteapi.infrastructure.scheduler;

import com.softbox.voteapi.application.useCase.schedule.SessionSchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "scheduling.enable")
public class CloseSessionScheduler {

    private final SessionSchedule sessionSchedule;

    @Scheduled(fixedRate = 30000)
    public Disposable execute() {
        return this.sessionSchedule.closeSection();
    }
}
