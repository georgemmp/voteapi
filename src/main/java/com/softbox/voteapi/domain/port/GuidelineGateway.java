package com.softbox.voteapi.domain.port;

import com.softbox.voteapi.domain.entity.guideline.Guideline;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GuidelineGateway {

    Mono<Guideline> save(Guideline guideline);
    Mono<Guideline> findById(String guidelineId);
    Flux<Guideline> findAllOpenedSessions();

}
