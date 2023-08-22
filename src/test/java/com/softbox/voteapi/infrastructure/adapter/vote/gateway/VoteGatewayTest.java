package com.softbox.voteapi.infrastructure.adapter.vote.gateway;

import com.softbox.voteapi.domain.entity.guideline.Guideline;
import com.softbox.voteapi.domain.entity.vote.Vote;
import com.softbox.voteapi.infrastructure.repository.VoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.*;

public class VoteGatewayTest {

    @Mock
    private VoteRepository repository;

    @InjectMocks
    private VoteGatewayImpl voteGateway;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindVoteByCpfAndGuidelineId() {
        Vote vote = Vote.builder()
                .guidelineId("x")
                .associateCpf("123456")
                .build();

        when(this.repository.findByAssociateCpfAndGuidelineId(anyString(), anyString())).thenReturn(just(vote));

        Mono<Vote> result = this.voteGateway.findByAssociateCpfAndGuidelineId(vote.getAssociateCpf(), vote.getGuidelineId());

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> {
                    assertEquals("x", r.getGuidelineId());
                    assertEquals("123456", r.getAssociateCpf());
                })
                .verifyComplete();
    }

    @Test
    public void shouldSaveVote() {
        Vote vote = Vote.builder()
                .guidelineId("x")
                .associateCpf("123456")
                .voteDescription("Sim")
                .build();

        when(this.repository.save(any(Vote.class))).thenReturn(just(vote));

        Mono<Vote> result = this.voteGateway.save(vote);

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> {
                    assertEquals("x", r.getGuidelineId());
                    assertEquals("123456", r.getAssociateCpf());
                    assertEquals("Sim", r.getVoteDescription());
                })
                .verifyComplete();
    }

    @Test
    public void shouldFindVoteByGuidelineId() {
        Vote vote = Vote.builder()
                .guidelineId("x")
                .associateCpf("123456")
                .build();

        when(this.repository.findByGuidelineId(anyString())).thenReturn(Flux.just(vote));

        Flux<Vote> result = this.voteGateway.findByGuidelineId(vote.getGuidelineId());

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> {
                    assertEquals("x", r.getGuidelineId());
                    assertEquals("123456", r.getAssociateCpf());
                })
                .verifyComplete();
    }
}
