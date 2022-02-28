package com.softbox.voteapi;

import com.softbox.voteapi.entities.Associate;
import com.softbox.voteapi.infrastructure.dto.AssociateDTO;
import com.softbox.voteapi.infrastructure.repositories.AssociateRepository;
import com.softbox.voteapi.services.associate.AssociateServiceImplementation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AssociateServiceTest {
    @Mock
    private AssociateRepository repository;

    @InjectMocks @Spy
    private AssociateServiceImplementation service;

    private Associate associate;

    @Before
    public void setup() {
        this.associate = Associate.builder()
                .name("Test")
                .cpf("11111111111")
                .build();
    }

    @Test
    public void shouldCreateNewAssociate() {
        AssociateDTO dto = AssociateDTO.builder()
                .cpf("11111111111")
                .name("Test")
                .build();

        Mockito.doReturn(Mono.empty()).when(this.service).save(dto);
        this.service.save(dto);
        Mockito.verify(this.service, Mockito.times(1)).save(dto);
    }
}
