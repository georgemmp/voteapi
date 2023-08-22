package com.softbox.voteapi.infrastructure.adapter.vote;

import com.softbox.voteapi.application.useCase.vote.SaveVote;
import com.softbox.voteapi.domain.entity.vote.Vote;
import com.softbox.voteapi.infrastructure.adapter.cpfValidator.CpfValidatorAdapter;
import com.softbox.voteapi.infrastructure.adapter.vote.mapper.VoteMapper;
import com.softbox.voteapi.infrastructure.api.dto.VoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ComputeVoteAdapter {

    private final SaveVote saveVote;
    private final CpfValidatorAdapter cpfValidatorAdapter;

    public Mono<Vote> execute(VoteDTO voteDTO, String guidelineId) {
        Vote vote = VoteMapper.mapper(voteDTO);
        return this.cpfValidatorAdapter.execute(vote.getAssociateCpf())
                .then(this.saveVote.execute(vote, guidelineId));
    }
}
