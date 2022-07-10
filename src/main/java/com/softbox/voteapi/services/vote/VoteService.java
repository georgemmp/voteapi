package com.softbox.voteapi.services.vote;

import com.softbox.voteapi.entities.Vote;
import com.softbox.voteapi.services.vote.model.VoteCountResponse;
import reactor.core.publisher.Mono;

public interface VoteService {
    Mono<Vote> processVote(Vote vote, String guidelineId);
    Mono<VoteCountResponse> countVotes();
}
