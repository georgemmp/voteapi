package com.softbox.voteapi.infrastructure.scheduler;

import com.softbox.voteapi.modules.guideline.services.GuidelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.scheduler.Schedulers;

@Component
@Slf4j
@RequiredArgsConstructor
public class SessionScheduler {
    private final GuidelineService service;

    @Scheduled(fixedRate = 300000)
    public Disposable closeSection() {
        return this.service.closeSessions()
                .doOnRequest(l -> log.info("Checking opened votes"))
                .subscribeOn(Schedulers.immediate())
                .subscribe();
    }
}
