//package com.example.microservices_demo.serviceclients;
//
//import com.example.microservices_demo.challenge.ChallengeAttempt;
//import com.example.microservices_demo.challenge.ChallengeSolvedEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Slf4j
//@Service
//public class GamificationServiceClient {
//
//    private final RestTemplate restTemplate;
//    private final String gamificationHostUrl;
//
//    @Autowired
//    public GamificationServiceClient(final RestTemplateBuilder builder,
//                                     @Value("${service.gamification.host}") final String gamificationHostUrl) {
//        this.restTemplate = builder.build();
//        this.gamificationHostUrl = gamificationHostUrl;
//    }
//
//    public boolean sendAttempt(final ChallengeAttempt attempt) {
//        try {
//            ChallengeSolvedEvent dto = new ChallengeSolvedEvent(attempt.getId(), attempt.isCorrect(),
//                                                          attempt.getFactorA(), attempt.getFactorB(),
//                                                          attempt.getUser().getId(), attempt.getUser().getAlias());
//            ResponseEntity<String> r = restTemplate.postForEntity(gamificationHostUrl + "/attempts", dto, String.class);
//            log.info("Gamification service response: {}", r.getStatusCode());
//            return r.getStatusCode().is2xxSuccessful();
//        } catch (Exception e) {
//            log.error("There was a problem sending the attempt.", e);
//            return false;
//        }
//    }
//}
