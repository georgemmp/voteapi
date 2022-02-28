package com.softbox.voteapi.infrastructure.http.routes;

import com.softbox.voteapi.infrastructure.http.handlers.AssociateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class AssociateRouter {

    @Bean
    public RouterFunction<ServerResponse> route(AssociateHandler handler) {
        return RouterFunctions
                .route(POST("/api/v1/associate").and(accept(MediaType.APPLICATION_JSON)), handler::save);
    }
}
