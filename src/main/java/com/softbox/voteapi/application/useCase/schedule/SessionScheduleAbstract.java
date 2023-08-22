package com.softbox.voteapi.application.useCase.schedule;

import com.softbox.voteapi.application.useCase.guideline.CloseSessions;
import com.softbox.voteapi.application.useCase.vote.CountVotes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.Disposable;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Slf4j
public abstract class SessionScheduleAbstract {

    private final CloseSessions closeSessions;
    private final CountVotes countVotes;

    @Value("${topic.name.producer}")
    private String topic;

    public Disposable closeSection() {
        return this.closeSessions.execute()
                .flatMap(guideline -> this.countVotes.execute(guideline.getGuidelineId()))
                .doOnNext(message -> this.sendMessage(message, topic))
                .doOnRequest(l -> log.info("Checking opened votes"))
                .subscribeOn(Schedulers.immediate())
                .subscribe();
    }
    protected abstract void sendMessage(Object object, String topic);
}
