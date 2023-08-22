package com.softbox.voteapi.application.useCase.associate;

import com.softbox.voteapi.domain.port.AssociateGateway;
import com.softbox.voteapi.domain.entity.associate.Associate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.*;

public class SaveAssociateTest {

    @InjectMocks
    private SaveAssociate saveAssociate;

    @Mock
    private AssociateGateway associateGateway;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldExecuteSaveAssociateUseCase() {
        Associate associate = Associate.builder()
                .cpf("123456")
                .name("Test")
                .associateId(UUID.randomUUID().toString())
                .build();

        when(this.associateGateway.findByCpf(anyString())).thenReturn(empty());
        when(this.associateGateway.save(any(Associate.class))).thenReturn(just(associate));

        Mono<Associate> result = this.saveAssociate.execute(associate);

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(r -> {
                    assertNotNull(r);
                    assertEquals("123456", r.getCpf());
                    assertEquals("Test", r.getName());
                })
                .verifyComplete();
    }
}
