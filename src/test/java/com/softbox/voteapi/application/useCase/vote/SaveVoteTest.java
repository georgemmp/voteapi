package com.softbox.voteapi.application.useCase.vote;

import com.softbox.voteapi.domain.port.GuidelineGateway;
import com.softbox.voteapi.domain.entity.guideline.Guideline;
import com.softbox.voteapi.domain.port.VoteGateway;
import com.softbox.voteapi.domain.entity.vote.Vote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.*;

public class SaveVoteTest {

    @Mock
    private VoteGateway voteGateway;

    @InjectMocks
    private SaveVote saveVote;

    @Mock
    private GuidelineGateway guidelineGateway;

    @Mock
    private VerifyIfAssociateVoted verifyIfAssociateVoted;

    private Vote vote;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        Guideline guideline = Guideline.builder()
                .guidelineId("x")
                .build();

        this.vote = Vote.builder()
                .associateCpf("123456")
                .guidelineId(guideline.getGuidelineId())
                .build();

        when(this.guidelineGateway.findById(anyString())).thenReturn(just(guideline));
        when(this.verifyIfAssociateVoted.execute(anyString(), anyString())).thenReturn(empty());
    }

    @Test
    public void shouldSaveVote() {
        when(this.voteGateway.save(any(Vote.class))).thenReturn(just(this.vote));

        Mono<Vote> result = this.saveVote.execute(this.vote, "x");

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> Assertions.assertNotNull(this.vote))
                .verifyComplete();
    }
}
