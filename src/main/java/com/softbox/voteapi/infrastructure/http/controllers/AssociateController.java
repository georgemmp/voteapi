package com.softbox.voteapi.infrastructure.http.controllers;

import com.softbox.voteapi.infrastructure.dto.AssociateDTO;
import com.softbox.voteapi.services.associate.AssociateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/associate")
public class AssociateController {
    @Autowired
    private AssociateService service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<Void> save(@Valid @RequestBody AssociateDTO dto) {
        return this.service.save(dto);
    }
}
