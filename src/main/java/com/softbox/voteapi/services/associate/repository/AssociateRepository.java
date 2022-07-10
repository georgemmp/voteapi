package com.softbox.voteapi.services.associate.repository;

import com.softbox.voteapi.entities.Associate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AssociateRepository extends ReactiveMongoRepository<Associate, String> {
    Mono<Associate> findByCpf(String cpf);
}
