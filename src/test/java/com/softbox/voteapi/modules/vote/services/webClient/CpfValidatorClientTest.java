package com.softbox.voteapi.modules.vote.services.webClient;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.softbox.voteapi.modules.IntegrationTest;
import com.softbox.voteapi.webClient.CpfValidatorClient;
import com.softbox.voteapi.webClient.dto.CpfValidatorResponse;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@IntegrationTest
public class CpfValidatorClientTest {

    @Autowired
    private CpfValidatorClient cpfValidatorClient;

    @Autowired
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        this.wireMockServer.resetRequests();
    }

    private final String CPF = "95963867002";

    @Test
    public void shouldCallCpfValidatorWebClient() {
        this.wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo("/users/" + CPF))
                        .willReturn(WireMock.aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE,
                                        MediaType.APPLICATION_JSON_VALUE)
                                .withStatus(HttpStatus.OK.value())
                                .withBody(responseBody()))
        );

        Mono<CpfValidatorResponse> result = this.cpfValidatorClient.validateCpf(CPF);

        StepVerifier.create(result)
                .assertNext(response -> {
                    Assert.assertNotNull(response);
                    Assert.assertEquals("ABLE_TO_VOTE", response.getStatus());
                });
    }

    private String responseBody() {
        return "{\n" +
                "   \"status\":\"ABLE_TO_VOTE\"\n" +
                "}";
    }
}
