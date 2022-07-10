package com.example.microservices_demo.challenge;

//import com.example.microservices_demo.serviceclients.GamificationServiceClient;
import com.example.microservices_demo.user.User;
import com.example.microservices_demo.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ChallengeServiceImpl implements IChallengeService {

    private final UserRepository userRepository;
    private final ChallengeAttemptRepository challengeAttemptRepository;
//    private final GamificationServiceClient gamificationServiceClient;
    private final ChallengeEventPub challengeEventPub;

    @Autowired
    public ChallengeServiceImpl(UserRepository userRepository,
                                ChallengeAttemptRepository challengeAttemptRepository,
                                ChallengeEventPub challengeEventPub) {
        this.userRepository = userRepository;
        this.challengeAttemptRepository = challengeAttemptRepository;
        this.challengeEventPub = challengeEventPub;
    }

    @Transactional
    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {
        User user = this.userRepository.findByAlias(attemptDTO.getUserAlias())
                .orElseGet(
                    () -> {
                        log.info("Creating new user with alias {}", attemptDTO.getUserAlias());
                        return this.userRepository.save(new User(attemptDTO.getUserAlias()));
                    }
                );

        boolean isCorrect = attemptDTO.getGuess() == attemptDTO.getFactorA() * attemptDTO.getFactorB();
        ChallengeAttempt challengeAttempt = new ChallengeAttempt(null,
                                                                    user,
                                                                    attemptDTO.getFactorA(),
                                                                    attemptDTO.getFactorB(),
                                                                    attemptDTO.getGuess(),
                                                                    isCorrect);

        var storedAttempt = this.challengeAttemptRepository.save(challengeAttempt);

//        boolean status = gamificationServiceClient.sendAttempt(storedAttempt);
//        log.info("Gamification service response: {}", status);
        challengeEventPub.challengeSolved(storedAttempt);

        return storedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String userAlias) {
        return this.challengeAttemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }

}
