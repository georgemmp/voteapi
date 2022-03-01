package com.softbox.voteapi.infrastructure.repositories;

import com.softbox.voteapi.entities.Guideline;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface GuidelineRepository extends ReactiveMongoRepository<Guideline, String> {
    @Query("{ 'session' : true }")
    Flux<Guideline> findAllSessionOpen();
}
