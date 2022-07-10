package com.example.microservices_demo.challenge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/attempts")
public class ChallengeAttemptController {

    private final IChallengeService challengeService;

    public ChallengeAttemptController(@Autowired IChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping
    ResponseEntity<ChallengeAttempt> postResult(@RequestBody ChallengeAttemptDTO challengeAttemptDTO) {
        return ResponseEntity.ok(this.challengeService.verifyAttempt(challengeAttemptDTO));
    }

    @GetMapping
    ResponseEntity<List<ChallengeAttempt>> getStatistics(@RequestParam("alias") String alias) {
        return ResponseEntity.ok(
                this.challengeService.getStatsForUser(alias)
        );
    }

}
