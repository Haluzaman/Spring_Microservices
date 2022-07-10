package com.example.microservices_demo.challenge;

import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Value
public class ChallengeAttemptDTO {

    @Min(1) @Max(99)
    int factorA;
    @Min(1) @Max(99)
    int factorB;
    @NotBlank
    String userAlias;
    @Positive(message = "How could you get a negative result here? Try again.")
    int guess;

}
