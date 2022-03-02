package com.softbox.voteapi.services.associate;

import com.softbox.voteapi.entities.Associate;
import com.softbox.voteapi.infrastructure.dto.AssociateDTO;
import com.softbox.voteapi.infrastructure.repositories.AssociateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class AssociateServiceImpl implements AssociateService {
    @Autowired
    private AssociateRepository repository;

    @Override
    public Mono<Void> save(AssociateDTO dto) {
        return this.repository.findByCpf(dto.getCpf())
                .flatMap(item -> {
                    if (Objects.nonNull(item)) {
                        log.error("CPF already exists");
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF already exists"));
                    }
                    return Mono.empty();
                }).then(this.createAssociate(dto));
    }

    private Mono<Void> createAssociate(AssociateDTO dto) {
        Associate associate = Associate.builder()
                .cpf(dto.getCpf())
                .name(dto.getName())
                .build();
        log.info("Created associate");
        return this.repository.save(associate).then(Mono.empty());
    }
}
