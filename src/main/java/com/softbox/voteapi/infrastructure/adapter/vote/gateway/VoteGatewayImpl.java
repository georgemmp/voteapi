package com.softbox.voteapi.infrastructure.adapter.vote.gateway;

import com.softbox.voteapi.domain.entity.vote.Vote;
import com.softbox.voteapi.domain.port.VoteGateway;
import com.softbox.voteapi.infrastructure.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VoteGatewayImpl implements VoteGateway {

    private final VoteRepository repository;

    @Override
    public Mono<Vote> findByAssociateCpfAndGuidelineId(String cpf, String guidelineId) {
        return this.repository.findByAssociateCpfAndGuidelineId(cpf, guidelineId);
    }

    @Override
    public Mono<Vote> save(Vote vote) {
        return this.repository.save(vote);
    }

    @Override
    public Flux<Vote> findByGuidelineId(String guidelineId) {
        return this.repository.findByGuidelineId(guidelineId);
    }
}
