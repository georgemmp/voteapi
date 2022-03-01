package com.softbox.voteapi.services.vote;

import com.softbox.voteapi.infrastructure.dto.VoteDTO;
import reactor.core.publisher.Mono;

public interface VoteService {
    Mono<Void> save(String guidelineId, VoteDTO dto);
}
