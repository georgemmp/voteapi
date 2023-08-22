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
import static reactor.core.publisher.Mono.just;

public class SaveGuidelineTest {

    @Mock
    private GuidelineGateway guidelineGateway;

    @InjectMocks
    private SaveGuideline saveGuideline;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveGuideline() {
        Guideline guideline = mock(Guideline.class);

        when(this.guidelineGateway.save(any(Guideline.class))).thenReturn(just(guideline));

        Mono<Guideline> result = this.saveGuideline.execute(guideline);

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(g -> assertNotNull(g))
                .verifyComplete();
    }
}
