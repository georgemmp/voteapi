package com.softbox.voteapi.infrastructure.repositories;

import com.softbox.voteapi.entities.Guideline;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface GuidelineRepository extends ReactiveMongoRepository<Guideline, String> {
}
