package com.softbox.voteapi.modules.associate.services;

import com.softbox.voteapi.modules.associate.entities.Associate;
import reactor.core.publisher.Mono;

public interface AssociateService {
    Mono<Associate> save(Associate associate);
    Mono<Associate> findByCpf(String cpf);
}
