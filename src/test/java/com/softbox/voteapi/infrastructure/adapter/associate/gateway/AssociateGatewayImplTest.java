package com.softbox.voteapi.infrastructure.adapter.associate.gateway;

import com.softbox.voteapi.domain.entity.associate.Associate;
import com.softbox.voteapi.infrastructure.adapter.associate.gateway.AssociateGatewayImpl;
import com.softbox.voteapi.infrastructure.repository.AssociateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssociateGatewayImplTest {

    @Mock
    private AssociateRepository repository;

    @InjectMocks
    private AssociateGatewayImpl associateGateway;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveAssociate() {
        Associate associate = Associate.builder()
                .associateId("x")
                .cpf("123456")
                .name("Test")
                .build();

        when(this.repository.save(any(Associate.class))).thenReturn(Mono.just(associate));

        Mono<Associate> result = this.associateGateway.save(associate);

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(a -> {
                    assertNotNull(a);
                    assertEquals("x", a.getAssociateId());
                    assertEquals("123456", a.getCpf());
                    assertEquals("Test", a.getName());
                })
                .verifyComplete();
    }

    @Test
    public void shouldFindAssociateByCPF() {
        Associate associate = Associate.builder()
                .associateId("x")
                .cpf("123456")
                .name("Test")
                .build();

        when(this.repository.findByCpf(anyString())).thenReturn(Mono.just(associate));

        Mono<Associate> result = this.associateGateway.findByCpf(associate.getCpf());

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(a -> {
                    assertNotNull(a);
                    assertEquals("x", a.getAssociateId());
                    assertEquals("123456", a.getCpf());
                    assertEquals("Test", a.getName());
                })
                .verifyComplete();
    }
}
