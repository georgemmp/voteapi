package com.softbox.voteapi.services.guideline;

import com.softbox.voteapi.entities.Guideline;
import com.softbox.voteapi.infrastructure.dto.GuidelineDTO;
import com.softbox.voteapi.infrastructure.repositories.GuidelineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class GuidelineServiceImplementation implements GuidelineService {
    @Autowired
    private GuidelineRepository repository;

    @Override
    public Mono<Void> save(GuidelineDTO guidelineDTO) {
        Guideline guideline = Guideline.builder()
                .description(guidelineDTO.getDescription())
                .session(false)
                .build();
        log.info("Guideline has been created");
        return this.repository.save(guideline).then(Mono.empty());
    }

    @Override
    public Mono<Void> updateSession(String id) {
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Guideline not found")))
                .flatMap(item -> {
                    if (item.getSession()) return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session already is open"));
                    item.setSession(true);
                    log.info("Session is open");
                    return this.repository.save(item);
                }).then(Mono.empty());
    }
}
