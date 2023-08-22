package com.softbox.voteapi.application.useCase.vote;

import com.softbox.voteapi.domain.port.VoteGateway;
import com.softbox.voteapi.domain.entity.vote.Vote;
import com.softbox.voteapi.domain.entity.vote.VoteCountResponse;
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

import java.util.Arrays;
import java.util.List;

public class CountVotesTest {

    @Mock
    private VoteGateway voteGateway;

    @InjectMocks
    private CountVotes countVotes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCountVotes() {

        Vote vote1 = Vote.builder()
                .guidelineId("x")
                .voteDescription("Sim")
                .build();

        Vote vote2 = Vote.builder()
                .guidelineId("x")
                .voteDescription("Sim")
                .build();

        List<Vote> votes = Arrays.asList(vote1, vote2);

        Mockito.when(this.voteGateway.findByGuidelineId(Mockito.anyString())).thenReturn(Flux.fromIterable(votes));

        Mono<VoteCountResponse> result = this.countVotes.execute(Mockito.anyString());

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(count -> Assertions.assertEquals(2, count.getYes()))
                .verifyComplete();
    }
}
