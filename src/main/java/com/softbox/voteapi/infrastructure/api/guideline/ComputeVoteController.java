package com.softbox.voteapi.infrastructure.api.guideline;

import com.softbox.voteapi.domain.entity.vote.Vote;
import com.softbox.voteapi.infrastructure.adapter.vote.ComputeVoteAdapter;
import com.softbox.voteapi.infrastructure.api.dto.VoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/guideline")
@RequiredArgsConstructor
public class ComputeVoteController {

    private final ComputeVoteAdapter computeVoteAdapter;

    @PostMapping(value = "/{guidelineId}/vote")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<Vote> computeVote(@RequestBody VoteDTO voteDTO, @PathVariable String guidelineId) {
        return this.computeVoteAdapter.execute(voteDTO, guidelineId);
    }
}
