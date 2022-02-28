package com.softbox.voteapi.infrastructure.repositories;

import com.softbox.voteapi.entities.Vote;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VoteRepository extends ReactiveMongoRepository<Vote, String> {
}
