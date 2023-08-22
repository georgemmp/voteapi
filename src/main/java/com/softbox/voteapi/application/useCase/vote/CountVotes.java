package com.softbox.voteapi.application.useCase.vote;

import com.softbox.voteapi.domain.port.VoteGateway;
import com.softbox.voteapi.domain.entity.vote.Vote;
import com.softbox.voteapi.domain.entity.vote.VoteCountResponse;
import com.softbox.voteapi.domain.type.VoteDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CountVotes {

    private final VoteGateway voteGateway;

    public Mono<VoteCountResponse> execute(String guidelineId) {
        return this.voteGateway.findByGuidelineId(guidelineId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Guideline not found")))
                .collectList()
                .map(votes -> {
                    Long yes = this.countVotes(votes, VoteDescription.YES);
                    Long no = this.countVotes(votes, VoteDescription.NO);

                    return VoteCountResponse.builder()
                            .no(no)
                            .yes(yes)
                            .build();
                });
    }

    private Long countVotes(List<Vote> votes, VoteDescription voteDescription) {
        return votes.stream()
                .filter(vote -> vote.equalsVoteDescription(voteDescription))
                .count();
    }
}
