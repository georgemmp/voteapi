package com.softbox.voteapi.infrastructure.adapter.guideline;

import com.softbox.voteapi.application.useCase.guideline.SaveGuideline;
import com.softbox.voteapi.domain.entity.guideline.Guideline;
import com.softbox.voteapi.infrastructure.api.dto.GuidelineDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SaveGuidelineAdapterTest {

    @Mock
    private SaveGuideline saveGuideline;

    @InjectMocks
    private SaveGuidelineAdapter adapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveGuideline() {
        GuidelineDTO guidelineDTO = mock(GuidelineDTO.class);
        Guideline guideline = mock(Guideline.class);

        when(this.saveGuideline.execute(any(Guideline.class))).thenReturn(Mono.just(guideline));

        Mono<Guideline> result = this.adapter.execute(guidelineDTO);

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> assertNotNull(r))
                .verifyComplete();
    }
}
