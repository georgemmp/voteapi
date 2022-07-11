package com.softbox.voteapi.modules.associate.repository;

import com.softbox.voteapi.modules.associate.entities.Associate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AssociateRepository extends ReactiveMongoRepository<Associate, String> {
    Mono<Associate> findByCpf(String cpf);
}
