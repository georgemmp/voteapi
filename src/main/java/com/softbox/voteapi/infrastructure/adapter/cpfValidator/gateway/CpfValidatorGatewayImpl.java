package com.softbox.voteapi.infrastructure.adapter.cpfValidator.gateway;

import com.softbox.voteapi.domain.entity.cpfValidator.CpfValidatorResponse;
import com.softbox.voteapi.domain.port.CpfValidatorGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CpfValidatorGatewayImpl implements CpfValidatorGateway {

    @Qualifier(value = "cpfValidatorUrl")
    private final WebClient webClient;

    @Override
    public Mono<CpfValidatorResponse> execute(String cpf) {
        return this.webClient.get()
                .uri("/users/" + cpf)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error ->
                        Mono.error(new RuntimeException(error.toString())))
                .onStatus(HttpStatus::is5xxServerError, error ->
                        Mono.error(new RuntimeException(error.toString())))
                .bodyToMono(CpfValidatorResponse.class)
                .doOnRequest(l -> log.info("Requesting to cpf validator service"))
                .doOnSuccess(l -> log.info("Request complete {}", l))
                .doOnError(error -> log.error("ERROR: {}", error));
    }
}
