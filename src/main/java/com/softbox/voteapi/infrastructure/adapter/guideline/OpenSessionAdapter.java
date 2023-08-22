package com.softbox.voteapi.infrastructure.adapter.guideline;

import com.softbox.voteapi.application.useCase.guideline.OpenSession;
import com.softbox.voteapi.domain.entity.guideline.Guideline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OpenSessionAdapter {

    private final OpenSession openSession;

    public Mono<Guideline> execute(String guidelineId) {
        return this.openSession.execute(guidelineId);
    }
}
