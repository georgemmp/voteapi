package com.softbox.voteapi.modules.associate.services;

import com.softbox.voteapi.modules.associate.AssociateGenerator;
import com.softbox.voteapi.modules.associate.entities.Associate;
import com.softbox.voteapi.modules.associate.repository.AssociateRepository;
import com.softbox.voteapi.modules.associate.services.AssociateServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class AssociateServiceTest {
    @InjectMocks
    private AssociateServiceImpl service;

    @Mock
    private AssociateRepository repository;

    private Associate validAssociate = AssociateGenerator.create().build();

    @BeforeEach
    public void setup() {
        when(this.repository.save(any(Associate.class))).thenReturn(Mono.just(validAssociate));
    }

    @Test
    public void shouldSaveAssociate() {
        Associate associate = AssociateGenerator.create().cpf("54423123").build();
        when(this.repository.findByCpf(anyString())).thenReturn(Mono.just(associate));

        Mono<Associate> result = this.service.save(validAssociate);

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(item -> {
                    Assert.assertNotNull(item);
                    Assert.assertEquals("123", item.getAssociateId());
                    Assert.assertEquals("Test",item.getName());
                    Assert.assertEquals("12345678", item.getCpf());
                })
                .verifyComplete();
    }

    @Test
    public void shouldThrowExceptionIfCPFAlreadyExists() {
        when(this.repository.findByCpf(anyString())).thenReturn(Mono.just(validAssociate));

        Mono<Associate> result = this.service.save(validAssociate);

        StepVerifier.create(result)
                .expectSubscription()
                .expectError()
                .verify();
    }
}
