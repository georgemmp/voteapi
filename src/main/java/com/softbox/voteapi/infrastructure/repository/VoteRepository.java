package com.softbox.voteapi.infrastructure.repository;

import com.softbox.voteapi.domain.entity.vote.Vote;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface VoteRepository extends ReactiveMongoRepository<Vote, String> {
    Mono<Vote> findByAssociateCpfAndGuidelineId(String cpf, String guidelineId);
    Flux<Vote> findByGuidelineId(String guidelineId);
}
