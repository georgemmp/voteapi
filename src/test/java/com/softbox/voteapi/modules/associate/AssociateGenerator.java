package com.softbox.voteapi.modules.associate;

import com.softbox.voteapi.modules.associate.entities.Associate;

public class AssociateGenerator {

    public static Associate.AssociateBuilder create() {
        return Associate.builder()
                .cpf("12345678")
                .name("Test")
                .associateId("123");
    }
}
