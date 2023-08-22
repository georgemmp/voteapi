package com.softbox.voteapi.infrastructure.adapter.cpfValidator;

import com.softbox.voteapi.application.useCase.cpfValidator.CpfValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CpfValidatorAdapter {

    private final CpfValidator cpfValidator;

    public Mono<Void> execute(String cpf) {
        return this.cpfValidator.validateCpfAndExecute(cpf);
    }
}
