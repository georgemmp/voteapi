package com.softbox.voteapi.infrastructure.http.requests.vote;

import com.softbox.voteapi.infrastructure.dto.ValidatorDTO;
import com.softbox.voteapi.infrastructure.http.requests.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class CPFVoteValidator {
    @Autowired
    private HttpRequest request;

    public Mono<ValidatorDTO> get(String baseUrl, String uri) {
        return this.request.createWebClient(baseUrl)
                .get()
                .uri(uri)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(ValidatorDTO.class);
                    } else if (clientResponse.statusCode().is4xxClientError()) {
                        return Mono.error(new ResponseStatusException(clientResponse.statusCode(), "Error response"));
                    } else {
                        return clientResponse
                                .createException()
                                .flatMap(Mono::error);
                    }
                });
    }
}
