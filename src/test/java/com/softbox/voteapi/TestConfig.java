package com.softbox.voteapi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
//import com.softbox.voteapi.infrastructure.api.webClient.cpfValidator.CpfValidatorClient;
import com.softbox.voteapi.domain.port.CpfValidatorGateway;
import com.softbox.voteapi.infrastructure.adapter.cpfValidator.gateway.CpfValidatorGatewayImpl;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class TestConfig {
    @Bean
    @Scope(value = "prototype")
    public WebTestClient webTestClient(ApplicationContext context) {
        return WebTestClient.bindToApplicationContext(context)
                .build();
    }

    @Bean
    @Scope(value = "prototype")
    public WebClient webClient(WireMockServer server) {
        return WebClient.builder().baseUrl(server.baseUrl()).build();
    }

    @Bean
    @Scope(value = "prototype")
    public WireMockServer webServer() {
        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        wireMockServer.start();
        return wireMockServer;
    }

    @Bean
    @Scope(value = "prototype")
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    @Scope(value = "prototype")
    public CpfValidatorGateway cpfValidatorClient(WebClient client) {
        return new CpfValidatorGatewayImpl(client);
    }
}
