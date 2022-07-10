package com.microservices.gamification.game.controller;

import com.microservices.gamification.game.domain.ChallengeSolvedEvent;
import com.microservices.gamification.game.domain.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GameEventHandler {

    private final GameService gameService;

    @Autowired
    public GameEventHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @RabbitListener(queues  = "${amqp.queue.gamification}")
    void handleMultiplicationSolved(final ChallengeSolvedEvent event) {
        log.info("Challenge Solved event received: {}", event.getAttemptId());
        try {
            gameService.newAttemptForUser(event);
        } catch (final Exception e) {
            log.error("Error when trying to process ChallengeSolvedEvent", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

}
