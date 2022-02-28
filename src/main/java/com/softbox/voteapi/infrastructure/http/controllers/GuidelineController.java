package com.softbox.voteapi.infrastructure.http.controllers;

import com.softbox.voteapi.infrastructure.dto.GuidelineDTO;
import com.softbox.voteapi.services.guideline.GuidelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/guideline")
public class GuidelineController {
    @Autowired
    private GuidelineService service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<Void> save(@RequestBody GuidelineDTO dto) {
        return this.service.save(dto);
    }

    @PatchMapping(value = "/{id}/session")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> openSession(@PathVariable String id) {
        return this.service.updateSession(id);
    }
}
