package com.softbox.voteapi.modules.guideline.services;

import com.softbox.voteapi.modules.guideline.GuidelineGenerator;
import com.softbox.voteapi.modules.guideline.entities.Guideline;
import com.softbox.voteapi.modules.guideline.repository.GuidelineRepository;
import com.softbox.voteapi.modules.guideline.services.GuidelineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class GuidelineServiceTest {
    @InjectMocks
    private GuidelineServiceImpl service;

    @Mock
    private GuidelineRepository repository;

    @Test
    public void shouldSaveGuideline() {
        Guideline validGuideline = GuidelineGenerator.create().build();
        when(this.repository.save(any(Guideline.class))).thenReturn(Mono.just(validGuideline));

        Mono<Guideline> result = this.service.save(validGuideline);

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(guideline -> {
                    assertNotNull(guideline);
                    assertEquals("123", validGuideline.getGuidelineId());
                    assertEquals("Test", validGuideline.getDescription());
                    assertEquals(false, validGuideline.getSession());
                })
                .verifyComplete();
    }

    @Test
    public void shouldOpenSession() {
        Guideline validGuideline = GuidelineGenerator.create().build();

        when(this.repository.findById(anyString())).thenReturn(Mono.just(validGuideline));
        when(this.repository.save(validGuideline)).thenReturn(Mono.just(validGuideline));

        Mono<Guideline> result = this.service.openSession(validGuideline.getGuidelineId());

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(guideline -> {
                    assertNotNull(guideline);
                    assertEquals(true, validGuideline.getSession());
                }).verifyComplete();
    }

    @Test
    public void shouldThrowExceptionToSessionAlreadyOpened() {
        Guideline validGuideline = GuidelineGenerator.create()
                .session(true)
                .build();

        when(this.repository.findById(anyString())).thenReturn(Mono.just(validGuideline));

        Mono<Guideline> result = this.service.openSession(validGuideline.getGuidelineId());

        StepVerifier.create(result)
                .expectSubscription()
                .expectError()
                .verify();
    }

    @Test
    public void shouldCloseSessions() {
        Guideline validGuideline = GuidelineGenerator.create()
                .date(LocalDateTime.MIN)
                .session(true)
                .build();

        when(this.repository.findAllSessionsOpen()).thenReturn(Flux.just(validGuideline));
        when(this.repository.save(validGuideline)).thenReturn(Mono.just(validGuideline));

        Flux<Guideline> result = this.service.closeSessions();

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(guideline -> {
                    assertNotNull(guideline);
                    assertEquals(false, validGuideline.getSession());
                }).verifyComplete();
    }

    @Test
    public void shouldFindGuidelineById() {
        Guideline validGuideline = GuidelineGenerator.create().build();

        when(this.repository.findById(anyString())).thenReturn(Mono.just(validGuideline));

        Mono<Guideline> result = this.service.findById("123");

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(guideline -> {
                    assertNotNull(guideline);
                    assertEquals("123", validGuideline.getGuidelineId());
                    assertEquals("Test", validGuideline.getDescription());
                    assertEquals(false, validGuideline.getSession());
                })
                .verifyComplete();
    }
}
