package com.microservices.gamification.game;

import com.microservices.gamification.game.data.BadgeRepository;
import com.microservices.gamification.game.data.ScoreRepository;
import com.microservices.gamification.game.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.microservices.gamification.game.domain.GameService.GameResult;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    private GameService gameService;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private BadgeRepository badgeRepository;

    @Mock
    private BadgeProcessor badgeProcessor;

    @BeforeEach
    public void setUp() {
        gameService = new GameServiceImpl(scoreRepository, badgeRepository, List.of(badgeProcessor));
    }

    @Test
    public void processCorrectAttemptTest() {
        Long userId = 1L;
        Long attemptId = 1L;
        var attempt = new ChallengeSolvedEvent(attemptId, true, 20, 70, userId, "john");
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);

        given(scoreRepository.getTotalScoreForUser(userId)).willReturn(Optional.of(10));
        given(scoreRepository.findByUserIdOrderByScoreTimestampDesc(userId)).willReturn(List.of(scoreCard));
        given(badgeProcessor.getBadgeType()).willReturn(BadgeType.LUCKY_NUMBER);
        given(badgeProcessor.processForOptionalBadge(10, List.of(scoreCard), attempt)).willReturn(Optional.of(BadgeType.LUCKY_NUMBER));

        final GameResult gameResult = gameService.newAttemptForUser(attempt);
        then(gameResult).isEqualTo(
                new GameResult(10,
                        List.of(BadgeType.LUCKY_NUMBER))
        );

        verify(scoreRepository).save(scoreCard);
        verify(badgeRepository).saveAll(List.of(new BadgeCard(userId, BadgeType.LUCKY_NUMBER)));
    }

    @Test
    public void processWrongAttemptTest() {
        // when
        GameResult gameResult = gameService.newAttemptForUser(
                new ChallengeSolvedEvent(10L, false, 10, 10, 1L, "john")
        );

        // then - shouldn't score anything
        then(gameResult).isEqualTo(new GameResult(0, List.of()));
    }
}
