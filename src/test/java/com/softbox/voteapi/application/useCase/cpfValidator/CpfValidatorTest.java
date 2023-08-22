package com.softbox.voteapi.application.useCase.cpfValidator;

import com.softbox.voteapi.domain.entity.cpfValidator.CpfValidatorResponse;
import com.softbox.voteapi.domain.port.CpfValidatorGateway;
import com.softbox.voteapi.domain.type.StatusCpfVote;
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

public class CpfValidatorTest {

    @Mock
    private CpfValidatorGateway cpfValidatorGateway;

    @InjectMocks
    private CpfValidator cpfValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldExecuteCpfValidatorUseCase() {
        CpfValidatorResponse cpfValidatorResponse = CpfValidatorResponse.builder()
                .status(StatusCpfVote.ABLE_TO_VOTE)
                .build();

        when(this.cpfValidatorGateway.execute(anyString())).thenReturn(Mono.just(cpfValidatorResponse));

        Mono<CpfValidatorResponse> result = this.cpfValidator.execute(anyString());

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> Assertions.assertEquals(StatusCpfVote.ABLE_TO_VOTE, r.getStatus()))
                .verifyComplete();
    }
}
