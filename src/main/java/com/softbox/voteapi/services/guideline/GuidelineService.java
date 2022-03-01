package com.softbox.voteapi.services.guideline;

import com.softbox.voteapi.entities.Guideline;
import com.softbox.voteapi.infrastructure.dto.GuidelineDTO;
import reactor.core.publisher.Mono;

public interface GuidelineService {
    Mono<Void> save(GuidelineDTO guidelineDTO);
    Mono<Void> updateSession(String id);
    Mono<Guideline> countVotes(String guidelineId);
}