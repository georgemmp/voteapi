package com.softbox.voteapi.application.useCase.vote;

import com.softbox.voteapi.domain.port.VoteGateway;
import com.softbox.voteapi.domain.entity.vote.Vote;
import com.softbox.voteapi.domain.type.VoteDescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.*;

public class VerifyIfAssociateVotedTest {

    @Mock
    private VoteGateway voteGateway;

    @InjectMocks
    private VerifyIfAssociateVoted verifyIfAssociateVoted;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldVerifyIfAssociateAlreadyVote() {
        Vote vote = Vote.builder()
                .guidelineId("x")
                .associateCpf("123456")
                .voteDescription(VoteDescription.NO.getDescription())
                .build();

        when(this.voteGateway.findByAssociateCpfAndGuidelineId(anyString(), anyString())).thenReturn(just(vote));

        Mono<Vote> result = this.verifyIfAssociateVoted.execute("123456", "x");

        StepVerifier.create(result)
                .expectSubscription()
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException)
                .verify();
    }
}
