package com.example.microservices_demo.challenge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    private final IChallengeGeneratorService challengeGeneratorService;

    public ChallengeController(@Autowired IChallengeGeneratorService challengeGeneratorService) {
        this.challengeGeneratorService = challengeGeneratorService;
    }

    @GetMapping("/random")
    public Challenge getRandomChallenge() {
        Challenge c = this.challengeGeneratorService.randomChallenge();
        log.info("Generating a random challenge: {}", c);
        return c;
    }
}
