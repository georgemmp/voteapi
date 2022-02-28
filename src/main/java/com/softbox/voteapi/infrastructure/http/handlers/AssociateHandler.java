package com.softbox.voteapi.infrastructure.http.handlers;

import com.softbox.voteapi.entities.Associate;
import com.softbox.voteapi.infrastructure.dto.AssociateDTO;
import com.softbox.voteapi.services.associate.AssociateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
public class AssociateHandler {
    @Autowired
    private AssociateService service;

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<AssociateDTO> dto = request.bodyToMono(AssociateDTO.class);
        return ServerResponse
                .created(request.uri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(dto.flatMap(this.service::save), Void.class));
    }
}
