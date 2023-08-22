package com.softbox.voteapi.application.useCase.vote;

import com.softbox.voteapi.domain.port.GuidelineGateway;
import com.softbox.voteapi.domain.port.VoteGateway;
import com.softbox.voteapi.domain.entity.vote.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class SaveVote {

    private final VoteGateway voteGateway;
    private final GuidelineGateway guidelineGateway;
    private final VerifyIfAssociateVoted verifyIfAssociateVoted;

    public Mono<Vote> execute(Vote vote, String guidelineId) {
        return this.guidelineGateway.findById(guidelineId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Guideline not found")))
                .then(this.verifyIfAssociateVoted.execute(vote.getAssociateCpf(), vote.getGuidelineId()))
                .filter(v -> v.validVote(vote.getAssociateCpf(), vote.getGuidelineId()))
                .flatMap(item -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Associate already vote")))
                .switchIfEmpty(this.voteGateway.save(vote))
                .then(Mono.just(vote));
    }
}
