package com.softbox.voteapi.infrastructure.scheduler;

import com.softbox.voteapi.services.guideline.GuidelineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.scheduler.Schedulers;

@Component
@Slf4j
public class CloseSectionScheduler {
    @Autowired
    private GuidelineService service;

    @Scheduled(fixedDelay = 30000)
    public Disposable closeSection() {
        log.info("Schedule executing");
        return this.service.closeSessions()
                .subscribeOn(Schedulers.immediate())
                .subscribe();
    }
}
