package com.softbox.voteapi.domain.port;

import com.softbox.voteapi.domain.entity.associate.Associate;
import reactor.core.publisher.Mono;

public interface AssociateGateway {

    Mono<Associate> save(Associate associate);
    Mono<Associate> findByCpf(String cpf);
}
