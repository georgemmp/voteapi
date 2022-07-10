package com.softbox.voteapi.services.guideline;

import com.softbox.voteapi.entities.Guideline;
import reactor.core.publisher.Mono;

public interface GuidelineService {
    Mono<Guideline> save(Guideline guideline);
    Mono<Guideline> openSession(String guidelineId);
    Mono<Void> closeSessions();
    Mono<Guideline> findById(String guidelineId);
}
