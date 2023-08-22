package com.softbox.voteapi.infrastructure.adapter.associate.mapper;

import com.softbox.voteapi.domain.entity.associate.Associate;
import com.softbox.voteapi.infrastructure.api.dto.AssociateDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AssociateMapperTest {

    @Test
    public void shouldTransferDtoDataToModel() {
        AssociateDTO dto = AssociateDTO.builder()
                .cpf("123456")
                .name("Test")
                .build();

        Associate associate = AssociateMapper.mapper(dto);

        assertNotNull(associate);
        assertEquals("123456", associate.getCpf());
        assertEquals("Test", associate.getName());
    }
}
