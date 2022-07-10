package com.example.microservices_demo.challenge;

import java.util.List;

public interface IChallengeService {

    /**
     * Verifies if an attempt is correct or not
     *
     * @return the resulting ChallengeAttempt object
     * */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);

    /**
     * Gets statistics for a given user.
     *
     * @param userAlias the users alias.
     * @return a list of the last 10 {@link ChallengeAttempt}
     * objects created by the user.
     * */
    List<ChallengeAttempt> getStatsForUser(String userAlias);

}
