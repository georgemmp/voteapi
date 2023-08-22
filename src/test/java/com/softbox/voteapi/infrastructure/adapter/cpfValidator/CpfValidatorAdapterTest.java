package com.softbox.voteapi.infrastructure.adapter.cpfValidator;

import com.softbox.voteapi.application.useCase.cpfValidator.CpfValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class CpfValidatorAdapterTest {

    @Mock
    private CpfValidator cpfValidator;

    @InjectMocks
    private CpfValidatorAdapter adapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldExecuteCpfValidatorAdapter() {
        when(this.cpfValidator.validateCpfAndExecute(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = this.adapter.execute("123456");

        StepVerifier.create(result)
                .expectSubscription()
                .verifyComplete();
    }
}
