package com.softbox.voteapi.modules.vote.repository;

import com.softbox.voteapi.modules.vote.entities.Vote;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VoteRepository extends ReactiveMongoRepository<Vote, String> {
    Mono<Vote> findByAssociateCpfAndGuidelineId(String cpf, String guidelineId);
    Flux<Vote> findByGuidelineId(String guidelineId);
}
