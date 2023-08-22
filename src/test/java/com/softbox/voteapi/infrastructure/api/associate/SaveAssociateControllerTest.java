package com.softbox.voteapi.infrastructure.api.associate;

import com.softbox.voteapi.IntegrationTest;
import com.softbox.voteapi.domain.entity.associate.Associate;
import com.softbox.voteapi.infrastructure.adapter.associate.SaveAssociateAdapter;
import com.softbox.voteapi.infrastructure.api.dto.AssociateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.when;

@IntegrationTest
@WebFluxTest(SaveAssociateController.class)
public class SaveAssociateControllerTest {

    @MockBean
    private SaveAssociateAdapter saveAssociateAdapter;

    @Autowired
    private WebTestClient webTestClient;

    private Associate associate = AssociateGenerator.create()
            .associateId("123")
            .build();

    @BeforeEach
    public void setup() {
        when(this.saveAssociateAdapter.execute(any(AssociateDTO.class))).thenReturn(Mono.just(associate));
    }

    @Test
    public void shouldCallAssociateControllerToSaveAssociate() {
        AssociateDTO associateDTO = AssociateDTO.builder()
                .cpf(associate.getCpf())
                .name(associate.getName())
                .build();

        webTestClient.post()
                .uri("/api/v1/associate")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(associateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Associate.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertEquals("123", response.getResponseBody().getAssociateId());
                    assertEquals("12345678", response.getResponseBody().getCpf());
                    assertEquals("Test", response.getResponseBody().getName());
                });
    }
}
