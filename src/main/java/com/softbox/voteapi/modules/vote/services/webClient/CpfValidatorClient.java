package com.softbox.voteapi.modules.vote.services.webClient;

import com.softbox.voteapi.modules.vote.services.webClient.dto.CpfValidatorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CpfValidatorClient {
    @Qualifier(value = "cpfValidatorUrl")
    private final WebClient webClient;

    public Mono<CpfValidatorResponse> cpfValidatorRequest(String cpf) {
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
