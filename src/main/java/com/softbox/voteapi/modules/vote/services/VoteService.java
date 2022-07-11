package com.softbox.voteapi.modules.vote.services;

import com.softbox.voteapi.modules.vote.entities.Vote;
import com.softbox.voteapi.modules.vote.entities.VoteCountResponse;
import reactor.core.publisher.Mono;

public interface VoteService {
    Mono<Vote> processVote(Vote vote, String guidelineId);
    Mono<VoteCountResponse> countVotes();
}
