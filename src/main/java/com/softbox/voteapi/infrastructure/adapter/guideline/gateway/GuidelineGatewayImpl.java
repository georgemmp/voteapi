package com.softbox.voteapi.infrastructure.adapter.guideline.gateway;

import com.softbox.voteapi.domain.entity.guideline.Guideline;
import com.softbox.voteapi.domain.port.GuidelineGateway;
import com.softbox.voteapi.infrastructure.repository.GuidelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GuidelineGatewayImpl implements GuidelineGateway {

    private final GuidelineRepository repository;

    @Override
    public Mono<Guideline> save(Guideline guideline) {
        return this.repository.save(guideline);
    }

    @Override
    public Mono<Guideline> findById(String guidelineId) {
        return this.repository.findById(guidelineId);
    }

    @Override
    public Flux<Guideline> findAllOpenedSessions() {
        return this.repository.findAllSessionsOpen();
    }
}
