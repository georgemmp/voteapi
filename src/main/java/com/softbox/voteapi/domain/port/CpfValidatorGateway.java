package com.softbox.voteapi.domain.port;

import com.softbox.voteapi.domain.entity.cpfValidator.CpfValidatorResponse;
import reactor.core.publisher.Mono;

public interface CpfValidatorGateway {

    Mono<CpfValidatorResponse> execute(String cpf);
}
