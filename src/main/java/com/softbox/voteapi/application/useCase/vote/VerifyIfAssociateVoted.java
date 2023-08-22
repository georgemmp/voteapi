package com.softbox.voteapi.application.useCase.vote;

import com.softbox.voteapi.domain.port.VoteGateway;
import com.softbox.voteapi.domain.entity.vote.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class VerifyIfAssociateVoted {

    private final VoteGateway voteGateway;

    public Mono<Vote> execute(String cpf, String guidelineId) {
        return this.voteGateway.findByAssociateCpfAndGuidelineId(cpf, guidelineId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Vote not found")))
                .filter(vote -> vote.validVote(cpf, guidelineId))
                .flatMap(vote -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Associate already vote")));
    }
}
