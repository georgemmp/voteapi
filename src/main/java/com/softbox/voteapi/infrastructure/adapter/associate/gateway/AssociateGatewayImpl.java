package com.softbox.voteapi.infrastructure.adapter.associate.gateway;

import com.softbox.voteapi.domain.entity.associate.Associate;
import com.softbox.voteapi.domain.port.AssociateGateway;
import com.softbox.voteapi.infrastructure.repository.AssociateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AssociateGatewayImpl implements AssociateGateway {

    private final AssociateRepository repository;

    @Override
    public Mono<Associate> save(Associate associate) {
        return this.repository.save(associate);
    }

    @Override
    public Mono<Associate> findByCpf(String cpf) {
        return this.repository.findByCpf(cpf);
    }
}
