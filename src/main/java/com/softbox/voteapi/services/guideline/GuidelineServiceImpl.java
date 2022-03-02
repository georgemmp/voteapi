package com.softbox.voteapi.services.guideline;

import com.softbox.voteapi.entities.Guideline;
import com.softbox.voteapi.infrastructure.dto.GuidelineDTO;
import com.softbox.voteapi.infrastructure.repositories.GuidelineRepository;
import com.softbox.voteapi.infrastructure.repositories.VoteRepository;
import com.softbox.voteapi.services.kafka.KafkaService;
import com.softbox.voteapi.shared.enums.VoteDescription;
import com.softbox.voteapi.shared.utils.DateHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GuidelineServiceImpl implements GuidelineService {
    @Autowired
    private GuidelineRepository repository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private KafkaService kafkaService;

    @Override
    public Mono<Void> save(GuidelineDTO guidelineDTO) {
        Guideline guideline = Guideline.builder()
                .description(guidelineDTO.getDescription())
                .result(null)
                .session(false)
                .build();
        log.info("Guideline has been created");
        return this.repository.save(guideline).then(Mono.empty());
    }

    @Override
    public Mono<Void> openSession(String id) {
        return this.checkGuideline(id)
                .flatMap(item -> {
                    if (item.getSession()) return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session already is open"));
                    item.setSession(true);
                    item.setDate(LocalDateTime.now());
                    log.info("Session is open");
                    return this.repository.save(item);
                }).then(Mono.empty());
    }

    @Override
    public Mono<Void> closeSessions() {
        return this.repository.findAllSessionOpen()
                .flatMap(item -> {
                    long diff = DateHandlerUtil.getDiffMinutes(item.getDate(), LocalDateTime.now());
                    if (diff >= 10) {
                        log.info("Session is closing");
                        item.setSession(false);
                        return countVotes(item.getGuidelineId(), item);
                    }
                    return Mono.empty();
                }).then(Mono.empty());
    }

    private Mono<Void> countVotes(String guidelineId, Guideline guideline) {
        List<String> yesList = new ArrayList<>();
        List<String> noList = new ArrayList<>();

        return this.checkVotes(guidelineId, yesList, noList)
                .then(saveVotesResult(guidelineId, yesList, noList));
    }

    private Mono<Void> saveVotesResult(String guidelineId, List<String> yesList, List<String> noList) {
        return this.checkGuideline(guidelineId)
                .flatMap(guideline -> {
                    log.info("results has been saved");
                    guideline.setResult("Sim: " + yesList.size() + "\n Não: " + noList.size() );
                    guideline.setSession(false);
                    this.kafkaService.sendMessage("vote-topic", guideline);
                    return this.repository.save(guideline);
                }).then(Mono.empty());
    }

    private Mono<Guideline> checkGuideline(String guidelineId) {
        return this.repository.findById(guidelineId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Guideline not found")));
    }

    private Mono<Void> checkVotes(String guidelineId, List<String> yesList, List<String> noList) {
        return this.voteRepository.findAllVotesByGuidelineId(guidelineId)
                .flatMap(item -> {
                    System.out.println(item);
                    if (item.getVoteDescription().equals(VoteDescription.YES.getDescription())) {
                        yesList.add(item.getVoteDescription());
                    }
                    if (item.getVoteDescription().equals(VoteDescription.NO.getDescription())) {
                        noList.add(item.getVoteDescription());
                    }
                    log.info("Votes computed");
                    return Mono.empty();
                })
                .then(Mono.empty());
    }
}
