package com.softbox.voteapi.modules.vote.services;

import com.softbox.voteapi.modules.associate.entities.Associate;
import com.softbox.voteapi.modules.guideline.entities.Guideline;
import com.softbox.voteapi.modules.vote.entities.Vote;
import com.softbox.voteapi.modules.associate.services.AssociateService;
import com.softbox.voteapi.modules.guideline.services.GuidelineService;
import com.softbox.voteapi.modules.vote.entities.VoteCountResponse;
import com.softbox.voteapi.modules.vote.repository.VoteRepository;
import com.softbox.voteapi.modules.vote.services.webClient.CpfValidatorClient;
import com.softbox.voteapi.modules.vote.services.webClient.dto.CpfValidatorResponse;
import com.softbox.voteapi.shared.enums.StatusCpfVote;
import com.softbox.voteapi.shared.enums.VoteDescription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteServiceImpl implements VoteService {
    private final VoteRepository repository;
    private final GuidelineService guidelineService;
    private final AssociateService associateService;
    private final CpfValidatorClient cpfValidatorClient;

    @Override
    public Mono<Vote> processVote(Vote vote, String guidelineId) {
        return this.findByGuidelineId(guidelineId)
                .then(this.findbyAssociateCpf(vote.getAssociateCpf()))
                .then(this.cpfValidatorClient.cpfValidatorRequest(vote.getAssociateCpf()))
                .flatMap(this::validateCpf)
                .then(this.verifyIfAssociateAlreadyVote(vote.getAssociateCpf(), guidelineId))
                .filter(item -> validVote(item, vote.getAssociateCpf(), guidelineId))
                .flatMap(item -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Associate already vote")))
                .switchIfEmpty(this.voteToSave(vote, guidelineId))
                .then(Mono.just(vote))
                .doOnRequest(l -> log.info("Processing vote"))
                .doOnSuccess(l -> log.info("Computed vote"))
                .doOnError(error -> log.error("ERROR: {}", error.getMessage()));
    }

    @Override
    public Mono<VoteCountResponse> countVotes(String guidelineId) {
        VoteCountResponse votes = new VoteCountResponse();

        String yes = VoteDescription.YES.getDescription();
        String no = VoteDescription.NO.getDescription();

        return this.countVouteByGuidelineAndDescription(guidelineId, yes)
                .flatMap(number -> this.setVotes(number, votes, guidelineId, yes))
                .then(this.countVouteByGuidelineAndDescription(guidelineId, no))
                .flatMap(number -> this.setVotes(number, votes, guidelineId, no))
                .doOnRequest(l -> log.info("Counting votes"))
                .doOnSuccess(l -> log.info("Votes counted: {}", votes))
                .doOnError(error -> log.error("ERROR: {}", error.getMessage()));
    }

    private Mono<Long> countVouteByGuidelineAndDescription(String guidelineId, String description) {
        return this.repository.findByGuidelineId(guidelineId)
                .filter(vote -> vote.getVoteDescription().equals(description))
                .count();
    }

    private Mono<VoteCountResponse> setVotes(Long number,
                                             VoteCountResponse votes,
                                             String guidelineId,
                                             String description) {
        if (description == VoteDescription.YES.getDescription()) {
            votes.setYes(number);
        } else {
            votes.setNo(number);
        }

        votes.setGuidelineId(guidelineId);
        return Mono.just(votes);
    }

    private Mono<Vote> voteToSave(Vote vote, String guidelineId) {
        vote.setVoteDescription(VoteDescription.toEnum(vote.getVoteDescription()).getDescription());
        vote.setAssociateCpf(vote.getAssociateCpf());
        vote.setGuidelineId(guidelineId);
        return this.repository.save(vote);
    }

    private Mono<Vote> verifyIfAssociateAlreadyVote(String cpf, String guidelineId) {
        return this.repository.findByAssociateCpfAndGuidelineId(cpf, guidelineId)
                .filter(vote -> this.validVote(vote, cpf, guidelineId))
                .flatMap(vote -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Associate already vote")));
    }

    private Mono<Guideline> findByGuidelineId(String id) {
        return this.guidelineService.findById(id)
                .filter(guideline -> guideline.getSession())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "The guideline session must be activated")));
    }

    private Mono<Associate> findbyAssociateCpf(String cpf) {
        return this.associateService.findByCpf(cpf);
    }

    private boolean validVote(Vote vote, String cpf, String guidelineId) {
        return (vote.getAssociateCpf().equals(cpf) &&
                vote.getGuidelineId().equals(guidelineId));
    }

    private Mono<Void> validateCpf(CpfValidatorResponse response) {
        if (response.getStatus() == StatusCpfVote.UNABLE_TO_VOTE) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "CPF unable to vote"));
        }

        return Mono.empty();
    }
}
