package com.softbox.voteapi.modules.guideline.repository;

import com.softbox.voteapi.modules.guideline.entities.Guideline;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface GuidelineRepository extends ReactiveMongoRepository<Guideline, String> {
    @Query("{ 'session' : true }")
    Flux<Guideline> findAllSessionsOpen();
}
