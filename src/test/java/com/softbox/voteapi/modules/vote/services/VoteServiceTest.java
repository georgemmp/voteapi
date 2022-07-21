package com.softbox.voteapi.modules.vote.services;

import com.softbox.voteapi.modules.associate.AssociateGenerator;
import com.softbox.voteapi.modules.associate.entities.Associate;
import com.softbox.voteapi.modules.associate.services.AssociateServiceImpl;
import com.softbox.voteapi.modules.guideline.GuidelineGenerator;
import com.softbox.voteapi.modules.guideline.entities.Guideline;
import com.softbox.voteapi.modules.guideline.services.GuidelineServiceImpl;
import com.softbox.voteapi.modules.vote.entities.Vote;
import com.softbox.voteapi.modules.vote.entities.VoteCountResponse;
import com.softbox.voteapi.modules.vote.repository.VoteRepository;
import com.softbox.voteapi.shared.enums.StatusCpfVote;
import com.softbox.voteapi.webClient.dto.CpfValidatorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {
    @InjectMocks
    private VoteServiceImpl service;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private GuidelineServiceImpl guidelineService;

    @Mock
    private AssociateServiceImpl associateService;

    @Test
    public void shouldProcessVote() {
        Guideline guideline = GuidelineGenerator.create()
                .session(true)
                .build();

        Vote vote = Vote.builder()
                .guidelineId("1")
                .voteDescription("Sim")
                .associateCpf("12345678")
                .voteId("123")
                .build();

        when(this.guidelineService.findById(anyString())).thenReturn(Mono.just(guideline));

        Associate associate = AssociateGenerator.create().build();

        lenient().when(this.associateService.findByCpf(anyString())).thenReturn(Mono.just(associate));

        CpfValidatorResponse response = CpfValidatorResponse.builder()
                .status(StatusCpfVote.ABLE_TO_VOTE)
                .build();

        when(this.voteRepository.findByAssociateCpfAndGuidelineId(anyString(), anyString()))
                .thenReturn(Mono.empty());

        when(this.voteRepository.save(vote)).thenReturn(Mono.just(vote));

        Mono<Vote> result = this.service.processVote(vote, guideline.getGuidelineId());

        StepVerifier.create(result)
                .expectSubscription()
                .assertNext(vote1 -> {
                    assertNotNull(vote1);
                    assertEquals("12345678", vote1.getAssociateCpf());
                    assertEquals("123", vote1.getVoteId());
                    assertEquals("123", vote1.getGuidelineId());
                    assertEquals("Sim", vote1.getVoteDescription());
                }).verifyComplete();
    }

    @Test
    public void cpfShouldBeUnableToVote() {
        Guideline guideline = GuidelineGenerator.create()
                .session(true)
                .build();

        Vote vote = Vote.builder()
                .guidelineId("1")
                .voteDescription("Sim")
                .associateCpf("12345678")
                .voteId("123")
                .build();

        when(this.guidelineService.findById(anyString())).thenReturn(Mono.just(guideline));

        Associate associate = AssociateGenerator.create().build();

        lenient().when(this.associateService.findByCpf(anyString())).thenReturn(Mono.just(associate));

        CpfValidatorResponse response = CpfValidatorResponse.builder()
                .status(StatusCpfVote.UNABLE_TO_VOTE)
                .build();

        when(this.voteRepository.findByAssociateCpfAndGuidelineId(anyString(), anyString()))
                .thenReturn(Mono.empty());

        when(this.voteRepository.save(vote)).thenReturn(Mono.just(vote));

        Mono<Vote> result = this.service.processVote(vote, guideline.getGuidelineId());

        StepVerifier.create(result)
                .expectError()
                .verify();
    }

    @Test
    public void shouldThrowExceptionCaseAssociateAlreadyVoted() {
        Guideline guideline = GuidelineGenerator.create()
                .session(true)
                .build();

        Vote vote = Vote.builder()
                .guidelineId(guideline.getGuidelineId())
                .voteDescription("Sim")
                .associateCpf("12345678")
                .voteId("123")
                .build();

        when(this.guidelineService.findById(anyString())).thenReturn(Mono.just(guideline));

        Associate associate = AssociateGenerator.create().build();

        lenient().when(this.associateService.findByCpf(anyString())).thenReturn(Mono.just(associate));

        CpfValidatorResponse response = CpfValidatorResponse.builder()
                .status(StatusCpfVote.ABLE_TO_VOTE)
                .build();

        when(this.voteRepository.findByAssociateCpfAndGuidelineId(anyString(), anyString()))
                .thenReturn(Mono.just(vote));

        when(this.voteRepository.save(vote)).thenReturn(Mono.just(vote));

        Mono<Vote> result = this.service.processVote(vote, guideline.getGuidelineId());

        StepVerifier.create(result)
                .expectError()
                .verify();
    }

    @Test
    public void shouldCountVotes() {
        Vote vote = Vote.builder()
                .guidelineId("1")
                .voteDescription("Sim")
                .associateCpf("12345678")
                .voteId("123")
                .build();

        when(this.voteRepository.findByGuidelineId(anyString())).thenReturn(Flux.just(vote));

        Mono<VoteCountResponse> result = this.service.countVotes("1");

        StepVerifier.create(result)
                .assertNext(voteCountResponse -> {
                    assertNotNull(voteCountResponse);
                    assertEquals(1, voteCountResponse.getYes().longValue());
                    assertEquals(0, voteCountResponse.getNo().longValue());
                }).verifyComplete();
    }
}
