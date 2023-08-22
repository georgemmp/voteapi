package com.softbox.voteapi.application.useCase.guideline;

import com.softbox.voteapi.domain.port.GuidelineGateway;
import com.softbox.voteapi.domain.entity.guideline.Guideline;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class OpenSession {

    private final GuidelineGateway guidelineGateway;

    public Mono<Guideline> execute(String guidelineId) {
        Mono<Guideline> guideline = this.guidelineGateway.findById(guidelineId);

        return this.guidelineGateway.findById(guidelineId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Guideline not found")))
                .filter(Guideline::verifyIfSessionIsOpen)
                .flatMap(mono -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Session is already open")))
                .then(guideline)
                .doOnNext(Guideline::openSession)
                .flatMap(this.guidelineGateway::save);
    }
}
