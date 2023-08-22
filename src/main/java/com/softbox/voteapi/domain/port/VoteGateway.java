package com.softbox.voteapi.domain.port;

import com.softbox.voteapi.domain.entity.vote.Vote;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VoteGateway {

    Mono<Vote> findByAssociateCpfAndGuidelineId(String cpf, String guidelineId);
    Mono<Vote> save(Vote vote);
    Flux<Vote> findByGuidelineId(String guidelineId);
}
