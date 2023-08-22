package com.softbox.voteapi.infrastructure.adapter.guideline;

import com.softbox.voteapi.application.useCase.guideline.OpenSession;
import com.softbox.voteapi.domain.entity.guideline.Guideline;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class OpenSessionAdapterTest {

    @Mock
    private OpenSession openSession;

    @InjectMocks
    private OpenSessionAdapter adapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldOpenSession() {
        Guideline guideline = Guideline.builder()
                .guidelineId("x")
                .session(true)
                .build();

        when(this.openSession.execute(anyString())).thenReturn(Mono.just(guideline));

        Mono<Guideline> result = this.adapter.execute(guideline.getGuidelineId());

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> Assertions.assertTrue(guideline.getSession()))
                .verifyComplete();
    }
}
