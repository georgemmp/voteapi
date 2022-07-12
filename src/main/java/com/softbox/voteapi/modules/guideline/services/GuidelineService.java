package com.softbox.voteapi.modules.guideline.services;

import com.softbox.voteapi.modules.guideline.entities.Guideline;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GuidelineService {
    Mono<Guideline> save(Guideline guideline);
    Mono<Guideline> openSession(String guidelineId);
    Flux<Guideline> closeSessions();
    Mono<Guideline> findById(String guidelineId);
}
