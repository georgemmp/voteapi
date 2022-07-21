package com.softbox.voteapi.modules.guideline.api;

import com.softbox.voteapi.modules.guideline.entities.Guideline;
import com.softbox.voteapi.modules.vote.entities.Vote;
import com.softbox.voteapi.modules.guideline.api.dto.GuidelineDTO;
import com.softbox.voteapi.modules.guideline.api.dto.VoteDTO;
import com.softbox.voteapi.modules.guideline.services.GuidelineService;
import com.softbox.voteapi.modules.vote.services.VoteService;
import com.softbox.voteapi.modules.vote.entities.VoteCountResponse;
import com.softbox.voteapi.webClient.CpfValidatorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/guideline")
@RequiredArgsConstructor
public class GuidelineController {
    private final GuidelineService service;
    private final VoteService voteService;
    private final CpfValidatorClient client;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<Guideline> save(@RequestBody GuidelineDTO guidelineDTO) {
        Guideline guideline = Guideline.builder()
                .description(guidelineDTO.getDescription())
                .build();

        return this.service.save(guideline);
    }

    @PatchMapping(value = "/{guidelineId}/session")
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<Guideline> openSession(@PathVariable String guidelineId) {
        return this.service.openSession(guidelineId);
    }

    @PostMapping(value = "/{guidelineId}/vote")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<Vote> computeVote(@RequestBody VoteDTO voteDTO, @PathVariable String guidelineId) {
        Vote vote = Vote.builder()
                .voteDescription(voteDTO.getVote())
                .associateCpf(voteDTO.getCpf())
                .build();

        return this.client.validateCpf(voteDTO.getCpf())
                .then(this.voteService.processVote(vote, guidelineId));
    }

    @GetMapping(value = "/{guidelineId}/vote-count")
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<VoteCountResponse> votesCount(@PathVariable String guidelineId) {
        return this.voteService.countVotes(guidelineId);
    }
}
