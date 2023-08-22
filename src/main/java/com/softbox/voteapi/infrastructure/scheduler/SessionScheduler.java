package com.softbox.voteapi.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "scheduling.enable")
public class SessionScheduler {
//    private final GuidelineService guidelineService;
//    private final VoteService voteService;
//    private final KafkaRepository kafkaRepository;
//
//    @Value("${topic.name.producer}")
//    private String voteTopic;
//
//    @Scheduled(fixedRate = 30000)
//    public Disposable closeSection() {
//        return this.guidelineService.closeSessions()
//                .flatMap(guideline -> this.voteService.countVotes(guideline.getGuidelineId()))
//                .doOnNext(this::sendMessage)
//                .doOnRequest(l -> log.info("Checking opened votes"))
//                .subscribeOn(Schedulers.immediate())
//                .subscribe();
//    }
//
//    public void sendMessage(Object object) {
//        if (Objects.nonNull(object)) {
//            log.info("Sending message to topic {}", voteTopic);
//            this.kafkaRepository.producer(object, voteTopic);
//        }
//    }
}
