package com.softbox.voteapi.infrastructure.adapter.associate;

import com.softbox.voteapi.application.useCase.associate.SaveAssociate;
import com.softbox.voteapi.domain.entity.associate.Associate;
import com.softbox.voteapi.infrastructure.adapter.associate.mapper.AssociateMapper;
import com.softbox.voteapi.infrastructure.api.dto.AssociateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SaveAssociateAdapterTest {

    @InjectMocks
    private SaveAssociateAdapter adapter;

    @Mock
    private SaveAssociate saveAssociate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveAssociate() {
        AssociateDTO dto = AssociateDTO.builder()
                .cpf("123456")
                .name("Test")
                .build();

        Associate associate = AssociateMapper.mapper(dto);

        when(this.saveAssociate.execute(any(Associate.class))).thenReturn(Mono.just(associate));

        Mono<Associate> result = this.adapter.execute(dto);

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(a -> {
                    assertNotNull(a);
                    assertEquals("123456", a.getCpf());
                    assertEquals("Test", a.getName());
                })
                .verifyComplete();
    }
}
