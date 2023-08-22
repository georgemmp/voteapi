package com.softbox.voteapi.infrastructure.adapter.guideline.gateway;

import com.softbox.voteapi.domain.entity.guideline.Guideline;
import com.softbox.voteapi.infrastructure.repository.GuidelineRepository;
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
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static reactor.core.publisher.Flux.*;

public class GuidelineGatewayTest {

    @Mock
    private GuidelineRepository repository;

    @InjectMocks
    private GuidelineGatewayImpl guidelineGateway;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveGuideline() {
        LocalDateTime dateTime = LocalDateTime.now();

        Guideline guideline = Guideline.builder()
                .session(false)
                .result("Test")
                .description("Test")
                .date(dateTime)
                .build();

        when(this.repository.save(any(Guideline.class))).thenReturn(Mono.just(guideline));

        Mono<Guideline> result = this.guidelineGateway.save(guideline);

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals(false, r.getSession());
                    assertEquals("Test", r.getDescription());
                    assertEquals("Test", r.getResult());
                    assertEquals(dateTime, r.getDate());
                }).verifyComplete();
    }

    @Test
    public void shouldFindGuidelineById() {
        Guideline guideline = Guideline.builder()
                .guidelineId("x")
                .build();

        when(this.repository.findById(anyString())).thenReturn(Mono.just(guideline));

        Mono<Guideline> result = this.guidelineGateway.findById("x");

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals("x", r.getGuidelineId());
                }).verifyComplete();
    }

    @Test
    public void shouldFindAllOpenedSessions() {
        Guideline guideline = Guideline.builder()
                .session(true)
                .build();

        when(this.repository.findAllSessionsOpen()).thenReturn(fromIterable(asList(guideline)));

        Mono<Object> result = this.guidelineGateway.findAllOpenedSessions()
                .collectList()
                .map(List::size);

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> Assertions.assertEquals("1", r.toString()))
                .verifyComplete();
    }
}
