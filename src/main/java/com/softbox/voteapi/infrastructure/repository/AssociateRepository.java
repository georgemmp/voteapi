package com.softbox.voteapi.infrastructure.repository;

import com.softbox.voteapi.domain.entity.associate.Associate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AssociateRepository extends ReactiveMongoRepository<Associate, String> {
    Mono<Associate> findByCpf(String cpf);
}
