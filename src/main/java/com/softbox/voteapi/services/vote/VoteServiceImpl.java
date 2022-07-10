package com.softbox.voteapi.services.vote;

import com.softbox.voteapi.entities.Associate;
import com.softbox.voteapi.entities.Guideline;
import com.softbox.voteapi.entities.Vote;
import com.softbox.voteapi.services.associate.AssociateService;
import com.softbox.voteapi.services.guideline.GuidelineService;
import com.softbox.voteapi.services.vote.model.VoteCountResponse;
import com.softbox.voteapi.services.vote.repository.VoteRepository;
import com.softbox.voteapi.shared.enums.VoteDescription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
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

    @Override
    public Mono<Vote> processVote(Vote vote, String guidelineId) {
        return this.findByGuidelineId(guidelineId)
                .then(this.findbyAssociateCpf(vote.getAssociateCpf()))
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
    public Mono<VoteCountResponse> countVotes() {
        VoteCountResponse votes = new VoteCountResponse();
        String noDescription = VoteDescription.NO.getDescription();
        String yesDesciption = VoteDescription.YES.getDescription();

        return this.countVotesByDescription(yesDesciption)
                .flatMap(item -> this.buildVotes(item, yesDesciption, votes))
                .then(this.countVotesByDescription(noDescription))
                .flatMap(item -> this.buildVotes(item, noDescription, votes))
                .doOnRequest(l -> log.info("Counting votes"))
                .doOnSuccess(l -> log.info("Votes counted: {}", votes))
                .doOnError(error -> log.info("ERROR: {}", error.getMessage()));
    }

    private Mono<VoteCountResponse> buildVotes(Long number,
                                               String description,
                                               VoteCountResponse response) {
        if(description.equals(VoteDescription.YES.getDescription())) {
            response.setYes(number);
            return Mono.just(response);
        }
        response.setNo(number);
        return Mono.just(response);
    }

    private Mono<Long> countVotesByDescription(String description) {
        Vote vote = Vote.builder()
                .voteDescription(description)
                .build();
        return this.repository.count(Example.of(vote));
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
}
