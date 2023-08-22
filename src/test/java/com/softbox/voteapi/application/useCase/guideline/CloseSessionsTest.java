package com.softbox.voteapi.application.useCase.guideline;

import com.softbox.voteapi.domain.port.GuidelineGateway;
import com.softbox.voteapi.domain.entity.guideline.Guideline;
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

import java.time.LocalDateTime;

public class CloseSessionsTest {

    @InjectMocks
    private CloseSessions closeSessions;

    @Mock
    private GuidelineGateway guidelineGateway;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCloseOpenedSessions() {
        Guideline guideline = Guideline.builder()
                .session(true)
                .date(LocalDateTime.MIN)
                .build();

        Mockito.when(this.guidelineGateway.findAllOpenedSessions()).thenReturn(Flux.just(guideline));
        Mockito.when(this.guidelineGateway.save(Mockito.any(Guideline.class))).thenReturn(Mono.just(guideline));

        Flux<Guideline> result = this.closeSessions.execute();

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(response -> Assertions.assertFalse(response.getSession()))
                .verifyComplete();
    }
}
