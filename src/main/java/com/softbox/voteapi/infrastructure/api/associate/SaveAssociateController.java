package com.softbox.voteapi.infrastructure.api.associate;

import com.softbox.voteapi.domain.entity.associate.Associate;
import com.softbox.voteapi.infrastructure.adapter.associate.SaveAssociateAdapter;
import com.softbox.voteapi.infrastructure.api.dto.AssociateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/associate")
@RequiredArgsConstructor
public class SaveAssociateController {
    private final SaveAssociateAdapter saveAssociateAdapter;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    Mono<Associate> save(@RequestBody AssociateDTO associateDTO) {
        return this.saveAssociateAdapter.execute(associateDTO);
    }
}
