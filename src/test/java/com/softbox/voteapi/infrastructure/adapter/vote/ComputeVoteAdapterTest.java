package com.softbox.voteapi.infrastructure.adapter.vote;

import com.softbox.voteapi.application.useCase.vote.SaveVote;
import com.softbox.voteapi.domain.entity.vote.Vote;
import com.softbox.voteapi.infrastructure.adapter.cpfValidator.CpfValidatorAdapter;
import com.softbox.voteapi.infrastructure.adapter.vote.mapper.VoteMapper;
import com.softbox.voteapi.infrastructure.api.dto.VoteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ComputeVoteAdapterTest {

    @Mock
    private CpfValidatorAdapter cpfValidatorAdapter;

    @Mock
    private SaveVote saveVote;

    @InjectMocks
    private ComputeVoteAdapter adapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldComputeVote() {
        VoteDTO dto = VoteDTO.builder()
                .associateCpf("123456")
                .voteDescription("Sim")
                .build();

        Vote vote = VoteMapper.mapper(dto);
        vote.setGuidelineId("x");

        when(this.cpfValidatorAdapter.execute(anyString())).thenReturn(Mono.empty());
        when(this.saveVote.execute(any(Vote.class), anyString())).thenReturn(Mono.just(vote));

        Mono<Vote> result = this.adapter.execute(dto, "x");

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> assertNotNull(r))
                .verifyComplete();
    }
}
