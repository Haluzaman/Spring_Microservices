package com.example.microservices_demo.challenge;

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
