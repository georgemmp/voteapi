package com.softbox.voteapi.modules.guideline.services;

import com.softbox.voteapi.modules.guideline.entities.Guideline;
import com.softbox.voteapi.modules.guideline.repository.GuidelineRepository;
import com.softbox.voteapi.shared.utils.DateHandlerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class GuidelineServiceImpl implements GuidelineService{
    private final GuidelineRepository repository;

    @Override
    public Mono<Guideline> save(Guideline guideline) {
        guideline.setDescription(guideline.getDescription());
        guideline.setSession(false);

        return this.repository.save(guideline)
                .doOnRequest(l -> log.info("Saving guideline {}", guideline))
                .doOnSuccess(l -> log.info("Guideline saved: {}", guideline))
                .doOnError(error -> log.error("ERROR: {}", error.getMessage()));
    }

    @Override
    public Mono<Guideline> openSession(String guidelineId) {
        return this.findById(guidelineId)
                .flatMap(this::verifyIfSessionIsOpen)
                .flatMap(this::sessionToOpen)
                .flatMap(repository::save)
                .doOnRequest(l -> log.info("Opening session from guideline {}", guidelineId))
                .doOnSuccess(l -> log.info("Guideline is open: {}", guidelineId))
                .doOnError(error -> log.error("ERROR: {}", error.getMessage()));
    }

    @Override
    public Mono<Void> closeSessions() {
        return this.repository.findAllSessionsOpen()
                .flatMap(this::sessionToClose)
                .doOnError(error -> log.error("ERROR: {}", error.getMessage()))
                .then();
    }

    @Override
    public Mono<Guideline> findById(String guidelineId) {
        return this.repository.findById(guidelineId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Guideline not found")));
    }

    private Mono<Guideline> verifyIfSessionIsOpen(Guideline guideline) {
        if (guideline.getSession()) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Session is already open"));
        }

        return Mono.just(guideline);
    }

    private Mono<Guideline> sessionToOpen(Guideline guideline) {
        guideline.setSession(true);
        guideline.setDate(LocalDateTime.now());
        return Mono.just(guideline);
    }

    private Mono<Guideline> sessionToClose(Guideline guideline) {
        long diff = DateHandlerUtil.getDiffMinutes(guideline.getDate(), LocalDateTime.now());

        if (diff >= 1L) {
            guideline.setSession(false);
        }

        return this.repository.save(guideline)
                .doOnSuccess(l -> log.info("Session close for guideline {}", guideline));
    }
}
