package com.softbox.voteapi.application.useCase.cpfValidator;

import com.softbox.voteapi.domain.entity.cpfValidator.CpfValidatorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

public abstract class CpfValidatorAbstract {

    public Mono<Void> validateCpfAndExecute(String cpf) {
        return this.execute(cpf)
                .filter(CpfValidatorResponse::checkCpf)
                .flatMap(mono -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "CPF unable to vote")))
                .switchIfEmpty(this.execute(cpf))
                .then(Mono.empty());
    }

    protected abstract Mono<CpfValidatorResponse> execute(String cpf);
}
