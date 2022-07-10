package com.softbox.voteapi.services.vote.repository;

import com.softbox.voteapi.entities.Vote;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface VoteRepository extends ReactiveMongoRepository<Vote, String> {
    Mono<Vote> findByAssociateCpfAndGuidelineId(String cpf, String guidelineId);
}
