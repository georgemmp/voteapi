package com.softbox.voteapi.application.useCase.guideline;

import com.softbox.voteapi.domain.port.GuidelineGateway;
import com.softbox.voteapi.domain.entity.guideline.Guideline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class CloseSessions {

    private final GuidelineGateway guidelineGateway;

    public Flux<Guideline> execute() {
        return this.guidelineGateway.findAllOpenedSessions()
                .doOnNext(Guideline::closeSession)
                .flatMap(this.guidelineGateway::save);
    }
}
