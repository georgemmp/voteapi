package com.softbox.voteapi.infrastructure.repositories;

import com.softbox.voteapi.entities.Vote;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface VoteRepository extends ReactiveMongoRepository<Vote, String> {
    @Query(value = "{ 'guideline.guidelineId' : ?0 }")
    Flux<Vote> findAllVotesByGuidelineId(String guidelineId);
}
