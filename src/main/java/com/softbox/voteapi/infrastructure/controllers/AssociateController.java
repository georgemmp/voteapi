package com.softbox.voteapi.infrastructure.controllers;

import com.softbox.voteapi.modules.associate.entities.Associate;
import com.softbox.voteapi.infrastructure.controllers.dto.AssociateDTO;
import com.softbox.voteapi.modules.associate.services.AssociateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/associate")
@RequiredArgsConstructor
public class AssociateController {
    private final AssociateService service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    Mono<Associate> save(@RequestBody AssociateDTO associateDTO) {
        Associate associate = Associate.builder()
                .name(associateDTO.getName())
                .cpf(associateDTO.getCpf())
                .build();

        return this.service.save(associate);
    }
}
