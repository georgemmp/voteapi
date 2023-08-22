package com.softbox.voteapi.application.useCase.associate;

import com.softbox.voteapi.domain.port.AssociateGateway;
import com.softbox.voteapi.domain.entity.associate.Associate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class SaveAssociate {

    private final AssociateGateway associateGateway;

    public Mono<Associate> execute(Associate associate) {
        return associateGateway.findByCpf(associate.getCpf())
                .flatMap(a -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "CPF already exists")))
                .switchIfEmpty(this.associateGateway.save(associate))
                .then(Mono.just(associate));
    }
}
