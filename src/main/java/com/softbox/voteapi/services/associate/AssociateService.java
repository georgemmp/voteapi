package com.softbox.voteapi.services.associate;

import com.softbox.voteapi.entities.Associate;
import com.softbox.voteapi.infrastructure.dto.AssociateDTO;
import reactor.core.publisher.Mono;

public interface AssociateService {
    Mono<Void> save(AssociateDTO dto);
}
