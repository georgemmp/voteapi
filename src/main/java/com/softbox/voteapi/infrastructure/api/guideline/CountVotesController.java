package com.softbox.voteapi.infrastructure.api.guideline;

import com.softbox.voteapi.domain.entity.vote.VoteCountResponse;
import com.softbox.voteapi.infrastructure.adapter.vote.CountVotesAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/guideline")
@RequiredArgsConstructor
public class CountVotesController {

    private final CountVotesAdapter countVotesAdapter;

    @GetMapping(value = "/{guidelineId}/vote")
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<VoteCountResponse> votesCount(@PathVariable String guidelineId) {
        return this.countVotesAdapter.execute(guidelineId);
    }
}
