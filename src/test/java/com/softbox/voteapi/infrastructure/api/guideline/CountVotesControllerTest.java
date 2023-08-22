package com.softbox.voteapi.infrastructure.api.guideline;

import com.softbox.voteapi.IntegrationTest;
import com.softbox.voteapi.domain.entity.vote.VoteCountResponse;
import com.softbox.voteapi.infrastructure.adapter.vote.CountVotesAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@IntegrationTest
@WebFluxTest(CountVotesController.class)
public class CountVotesControllerTest {

    @MockBean
    private CountVotesAdapter adapter;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldExecuteCountVotesController() {
        VoteCountResponse response = VoteCountResponse.builder()
                .no(2L)
                .yes(6L)
                .guidelineId("1")
                .build();

        when(this.adapter.execute(anyString())).thenReturn(Mono.just(response));

        this.webTestClient.get()
                .uri("/api/v1/guideline/1/vote")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(VoteCountResponse.class);
    }
}
