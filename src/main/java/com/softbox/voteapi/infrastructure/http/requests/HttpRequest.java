package com.softbox.voteapi.infrastructure.http.requests;

import org.springframework.web.reactive.function.client.WebClient;

public interface HttpRequest<T> {
    WebClient createWebClient(String baseUrl);
}
