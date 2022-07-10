package com.microservices.gamification.game.badgeProcessors;

import com.microservices.gamification.game.domain.BadgeProcessor;
import com.microservices.gamification.game.domain.BadgeType;
import com.microservices.gamification.game.domain.ChallengeSolvedEvent;
import com.microservices.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public class LuckyNumberBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedEvent solvedDTO) {
        return solvedDTO.getFactorA() == 42 || solvedDTO.getFactorB() == 42 ?
                Optional.of(BadgeType.LUCKY_NUMBER) :
                Optional.empty();
    }

    @Override
    public BadgeType getBadgeType() {
        return BadgeType.LUCKY_NUMBER;
    }
}
