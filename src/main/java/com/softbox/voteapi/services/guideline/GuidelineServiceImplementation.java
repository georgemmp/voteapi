package com.softbox.voteapi.services.guideline;

import com.softbox.voteapi.entities.Guideline;
import com.softbox.voteapi.infrastructure.dto.GuidelineDTO;
import com.softbox.voteapi.infrastructure.repositories.GuidelineRepository;
import com.softbox.voteapi.infrastructure.repositories.VoteRepository;
import com.softbox.voteapi.shared.enums.VoteDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GuidelineServiceImplementation implements GuidelineService {
    @Autowired
    private GuidelineRepository repository;

    @Autowired
    private VoteRepository voteRepository;

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
        return this.checkGuideline(id)
                .flatMap(item -> {
                    if (item.getSession()) return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session already is open"));
                    item.setSession(true);
                    log.info("Session is open");
                    return this.repository.save(item);
                }).then(Mono.empty());
    }

    @Override
    public Mono<Guideline> countVotes(String guidelineId) {
        List<String> yesList = new ArrayList<>();
        List<String> noList = new ArrayList<>();

        return this.checkVotes(guidelineId, yesList, noList)
                .then(saveVotesResult(guidelineId, yesList, noList));
    }

    private Mono<Guideline> saveVotesResult(String guidelineId, List<String> yesList, List<String> noList) {
        return this.checkGuideline(guidelineId)
                .flatMap(guideline -> {
                    guideline.setResult("Sim: " + yesList.size() + "\n Não: " + noList.size() );
                    return this.repository.save(guideline);
                });
    }

    private Mono<Guideline> checkGuideline(String guidelineId) {
        return this.repository.findById(guidelineId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Guideline not found")));
    }

    private Mono<Void> checkVotes(String guidelineId, List<String> yesList, List<String> noList) {
        return this.voteRepository.findAllVotesByGuidelineId(guidelineId)
                .flatMap(item -> {
                    if (item.getVoteDescription().equals(VoteDescription.YES.getDescription()))
                        yesList.add(item.getVoteDescription());
                    if (item.getVoteDescription().equals(VoteDescription.NO.getDescription()))
                        noList.add(item.getVoteDescription());
                    log.info("Votes computed");
                    return Mono.empty();
                })
                .then(Mono.empty());
    }
}
