package com.softbox.voteapi.services.associate;

import com.softbox.voteapi.entities.Associate;
import com.softbox.voteapi.infrastructure.dto.AssociateDTO;
import com.softbox.voteapi.infrastructure.repositories.AssociateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class AssociateServiceImplementation implements AssociateService {
    @Autowired
    private AssociateRepository repository;

    @Override
    public Mono<Void> save(AssociateDTO dto) {
        this.repository.findByCpf(dto.getCpf())
                .flatMap(associate -> {
                    if (Objects.nonNull(associate.getCpf())) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF already exists"));
                    } else {
                        return null;
                    }
                }).toFuture();

        Associate associate = Associate.builder()
                .cpf(dto.getCpf())
                .name(dto.getName())
                .build();

        return this.repository.save(associate).then(Mono.empty());
    }
}
