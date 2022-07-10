package com.softbox.voteapi.services.associate;

import com.softbox.voteapi.entities.Associate;
import reactor.core.publisher.Mono;

public interface AssociateService {
    Mono<Associate> save(Associate associate);
    Mono<Associate> findByCpf(String cpf);
}
