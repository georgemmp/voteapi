package com.softbox.voteapi.application.useCase.cpfValidator;

import com.softbox.voteapi.domain.entity.cpfValidator.CpfValidatorResponse;
import com.softbox.voteapi.domain.port.CpfValidatorGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CpfValidator extends CpfValidatorAbstract {

    private final CpfValidatorGateway cpfValidatorGateway;

    @Override
    protected Mono<CpfValidatorResponse> execute(String cpf) {
        return this.cpfValidatorGateway.execute(cpf);
    }
}
