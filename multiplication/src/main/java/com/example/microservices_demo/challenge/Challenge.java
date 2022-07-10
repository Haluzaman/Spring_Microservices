package com.example.microservices_demo.challenge;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/*
 * Challenge to solve a multiplication ( a * b )
 * */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Challenge {
    private final int factorA;
    private final int factorB;
}
