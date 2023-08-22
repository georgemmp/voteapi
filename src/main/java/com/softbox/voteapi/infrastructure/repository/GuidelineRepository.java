package com.softbox.voteapi.infrastructure.repository;

import com.softbox.voteapi.domain.entity.guideline.Guideline;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GuidelineRepository extends ReactiveMongoRepository<Guideline, String> {
    @Query("{ 'session' : true }")
    Flux<Guideline> findAllSessionsOpen();
}
