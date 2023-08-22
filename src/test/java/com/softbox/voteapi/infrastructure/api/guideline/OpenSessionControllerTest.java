package com.softbox.voteapi.infrastructure.api.guideline;

import com.softbox.voteapi.IntegrationTest;
import com.softbox.voteapi.domain.entity.guideline.Guideline;
import com.softbox.voteapi.infrastructure.adapter.guideline.OpenSessionAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@IntegrationTest
@WebFluxTest(OpenSessionController.class)
public class OpenSessionControllerTest {

    @MockBean
    private OpenSessionAdapter openSessionAdapter;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldExecuteOpenSessionController() {
        Guideline guideline = Guideline.builder()
                .session(true)
                .guidelineId("1")
                .build();

        when(this.openSessionAdapter.execute(anyString())).thenReturn(Mono.just(guideline));

        this.webTestClient.patch()
                .uri("/api/v1/guideline/1/session")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Guideline.class);
    }
}
