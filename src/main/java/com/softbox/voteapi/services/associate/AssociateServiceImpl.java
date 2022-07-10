package com.softbox.voteapi.services.associate;

import com.softbox.voteapi.entities.Associate;
import com.softbox.voteapi.services.associate.repository.AssociateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Service
public class AssociateServiceImpl implements AssociateService{
    private final AssociateRepository repository;

    @Override
    public Mono<Associate> save(Associate associate) {
        return this.findByCpf(associate.getCpf())
                .filter(item -> this.validateCpfAlreadyExists(item, associate.getCpf()))
                .flatMap(item -> this.throwCPFException())
                .switchIfEmpty(this.repository.save(associate))
                .then(Mono.just(associate))
                .doOnRequest(l -> log.info("Saving associate {}", associate))
                .doOnSuccess(l -> log.info("Associate saved {}", associate))
                .doOnError(error -> log.info("ERROR: {}", error.getMessage()));
    }

    @Override
    public Mono<Associate> findByCpf(String cpf) {
        return this.repository.findByCpf(cpf)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "CPF not found")));
    }

    private boolean validateCpfAlreadyExists(Associate associate, String cpf) {
        return associate.getCpf().equals(cpf);
    }

    private Mono<Object> throwCPFException() {
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "CPF already exists"));
    }
}
