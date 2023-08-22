package com.softbox.voteapi.application.useCase.guideline;

import com.softbox.voteapi.domain.port.GuidelineGateway;
import com.softbox.voteapi.domain.entity.guideline.Guideline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class SaveGuideline {

    private final GuidelineGateway guidelineGateway;

    public Mono<Guideline> execute(Guideline guideline) {
        guideline.setSession(false);

        return this.guidelineGateway.save(guideline);
    }
}
