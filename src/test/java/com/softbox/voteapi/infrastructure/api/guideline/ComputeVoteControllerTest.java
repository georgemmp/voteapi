package com.softbox.voteapi.infrastructure.api.guideline;

import com.softbox.voteapi.IntegrationTest;
import com.softbox.voteapi.domain.entity.vote.Vote;
import com.softbox.voteapi.infrastructure.adapter.vote.ComputeVoteAdapter;
import com.softbox.voteapi.infrastructure.api.dto.VoteDTO;
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
@WebFluxTest(ComputeVoteController.class)
public class ComputeVoteControllerTest {

    @MockBean
    private ComputeVoteAdapter adapter;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldExecuteComputeVoteController() {
        Vote vote = Vote.builder()
                .guidelineId("1")
                .voteDescription("Sim")
                .associateCpf("123456")
                .build();

        when(this.adapter.execute(any(VoteDTO.class), anyString())).thenReturn(Mono.just(vote));

        VoteDTO dto = VoteDTO.builder()
                .associateCpf("123456")
                .voteDescription("Sim")
                .build();

        this.webTestClient.post()
                .uri("/api/v1/guideline/1/vote")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(dto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Vote.class);
    }
}
