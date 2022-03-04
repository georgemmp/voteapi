package com.softbox.voteapi.services.vote;

import com.softbox.voteapi.entities.Vote;
import com.softbox.voteapi.infrastructure.dto.VoteDTO;
import com.softbox.voteapi.infrastructure.http.requests.vote.CPFVoteValidator;
import com.softbox.voteapi.infrastructure.repositories.AssociateRepository;
import com.softbox.voteapi.infrastructure.repositories.GuidelineRepository;
import com.softbox.voteapi.infrastructure.repositories.VoteRepository;
import com.softbox.voteapi.shared.enums.VoteDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private GuidelineRepository guidelineRepository;

    @Autowired
    private CPFVoteValidator request;

    @Value("${cpf-validator-url}")
    private String cpfValidatorUrl;

    @Override
    public Mono<Void> save(String guidelineId, VoteDTO dto) {
        Vote vote = Vote.builder()
                .voteDescription(VoteDescription.toEnum(dto.getVote()).getDescription())
                .build();

        return this.requestValidator(dto.getCpf())
                .then(this.checkAssociate(vote, dto))
                .then(this.checkIfAssociateVoted(dto.getCpf(), guidelineId))
                .then(this.checkGuideline(vote, guidelineId))
                .then(this.voteRepository.save(vote))
                .then(Mono.empty());
    }

    private Mono<Void> checkAssociate(Vote vote, VoteDTO dto) {
        return this.associateRepository
                .findByCpf(dto.getCpf())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "CPF not found")))
                .flatMap(associate -> {
                    log.info("Associate checked");
                    vote.setAssociate(associate);
                    return Mono.empty();
                }).then(Mono.empty());
    }

    private Mono<Void> checkGuideline(Vote vote, String guidelineId) {
        return this.guidelineRepository.findById(guidelineId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Guideline not found")))
                .flatMap(guideline -> {
                    if (!guideline.getSession()) return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session is not open"));
                    log.info("Guideline checked");
                    vote.setGuideline(guideline);;
                    return Mono.empty();
                }).then(Mono.empty());
    }

    private Mono<Void> checkIfAssociateVoted(String cpf, String guidelineId) {
        return this.voteRepository
                .findAll()
                .flatMap(vote -> {
                    if (vote.getAssociate().getCpf().equals(cpf) && vote.getGuideline().getGuidelineId().equals(guidelineId)) {
                        log.info("Associate already voted about this guideline");
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Associate already voted about this guideline"));
                    }
                    log.info("Associate is ok to vote");
                    return Mono.empty();
                }).then(Mono.empty());
    }

    private Mono<Void> requestValidator(String cpf) {
        return this.request.get(cpfValidatorUrl, "/users/".concat(cpf))
                .flatMap(item -> {
                    log.info(item.getStatus());
                    if(!item.getStatus().equals("ABLE_TO_VOTE")){
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Associate is not able to vote"));
                    }
                    return Mono.just(item);
                }).then(Mono.empty());
    }
}
