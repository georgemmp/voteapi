package com.softbox.voteapi.infrastructure.api.guideline;

import com.softbox.voteapi.IntegrationTest;
import com.softbox.voteapi.domain.entity.guideline.Guideline;
import com.softbox.voteapi.infrastructure.adapter.guideline.SaveGuidelineAdapter;
import com.softbox.voteapi.infrastructure.adapter.guideline.mapper.GuidelineMapper;
import com.softbox.voteapi.infrastructure.api.dto.GuidelineDTO;
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
@WebFluxTest(SaveGuidelineController.class)
public class SaveGuidelineControllerTest {

    @MockBean
    private SaveGuidelineAdapter saveGuidelineAdapter;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldExecuteSaveGuidelineController() {
        GuidelineDTO dto = GuidelineDTO.builder()
                .description("Test")
                .build();

        Guideline guideline = GuidelineMapper.mapper(dto);

        when(this.saveGuidelineAdapter.execute(any(GuidelineDTO.class))).thenReturn(Mono.just(guideline));

        this.webTestClient.post()
                .uri("/api/v1/guideline")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(dto)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Guideline.class);
    }
}
