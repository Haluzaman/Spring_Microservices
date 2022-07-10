package com.microservices.gamification.game.domain;

import lombok.Value;

@Value
public class ChallengeSolvedEvent {

    Long attemptId;
    Boolean correct;
    int factorA;
    int factorB;
    Long userId;
    String userAlias;
}
