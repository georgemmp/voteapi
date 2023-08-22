package com.softbox.voteapi.infrastructure.adapter.vote;

import com.softbox.voteapi.application.useCase.vote.CountVotes;
import com.softbox.voteapi.domain.entity.vote.VoteCountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CountVotesAdapterTest {

    @Mock
    private CountVotes countVotes;

    @InjectMocks
    private CountVotesAdapter adapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCountVotes() {
        VoteCountResponse voteCountResponse = mock(VoteCountResponse.class);

        when(this.adapter.execute(anyString())).thenReturn(Mono.just(voteCountResponse));

        Mono<VoteCountResponse> result = this.countVotes.execute(anyString());

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> assertNotNull(r))
                .verifyComplete();
    }
}
