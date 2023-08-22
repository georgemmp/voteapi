package com.softbox.voteapi.application.useCase.guideline;

import com.softbox.voteapi.domain.port.GuidelineGateway;
import com.softbox.voteapi.domain.entity.guideline.Guideline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.*;

public class OpenSessionTest {

    @Mock
    private GuidelineGateway guidelineGateway;

    @InjectMocks
    private OpenSession openSession;


    @BeforeEach
    public void seteup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void shouldOpenSession() {
        Guideline guideline = Guideline.builder()
                .guidelineId("x")
                .session(false)
                .build();

        when(this.guidelineGateway.findById(anyString())).thenReturn(just(guideline));
        when(this.guidelineGateway.save(any(Guideline.class))).thenReturn(just(guideline));

        Mono<Guideline> result = this.openSession.execute(anyString());

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(g -> assertTrue(g.getSession()))
                .verifyComplete();
    }
}
