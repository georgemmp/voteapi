package com.softbox.voteapi.infrastructure.repositories;

import com.softbox.voteapi.entities.Associate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AssociateRepository extends ReactiveMongoRepository<Associate, String> {
}
