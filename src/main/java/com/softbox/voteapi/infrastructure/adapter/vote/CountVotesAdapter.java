package com.softbox.voteapi.infrastructure.adapter.vote;

import com.softbox.voteapi.application.useCase.vote.CountVotes;
import com.softbox.voteapi.domain.entity.vote.VoteCountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CountVotesAdapter {

    private final CountVotes countVotes;

    public Mono<VoteCountResponse> execute(String guidelineId) {
        return this.countVotes.execute(guidelineId);
    }
}
