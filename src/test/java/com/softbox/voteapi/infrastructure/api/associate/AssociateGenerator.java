package com.softbox.voteapi.infrastructure.api.associate;

import com.softbox.voteapi.domain.entity.associate.Associate;

public class AssociateGenerator {

    public static Associate.AssociateBuilder create() {
        return Associate.builder()
                .cpf("12345678")
                .name("Test")
                .associateId("123");
    }
}
