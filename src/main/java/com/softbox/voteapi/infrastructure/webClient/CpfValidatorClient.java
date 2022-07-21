package com.softbox.voteapi.webClient;

import com.softbox.voteapi.shared.enums.StatusCpfVote;
import com.softbox.voteapi.webClient.dto.CpfValidatorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CpfValidatorClient {
    @Qualifier(value = "cpfValidatorUrl")
    private final WebClient webClient;

    public Mono<CpfValidatorResponse> validateCpf(String cpf) {
        return this.cpfValidatorRequest(cpf)
                .flatMap(this::checkCpf);
    }

    private Mono<CpfValidatorResponse> cpfValidatorRequest(String cpf) {
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

    private Mono<CpfValidatorResponse> checkCpf(CpfValidatorResponse response) {
        if (response.getStatus() == StatusCpfVote.UNABLE_TO_VOTE) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "CPF unable to vote"));
        }
        return Mono.just(response);
    }
}
