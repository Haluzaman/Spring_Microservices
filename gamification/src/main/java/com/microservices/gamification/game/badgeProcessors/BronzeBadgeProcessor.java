package com.microservices.gamification.game.badgeProcessors;

import com.microservices.gamification.game.domain.BadgeProcessor;
import com.microservices.gamification.game.domain.BadgeType;
import com.microservices.gamification.game.domain.ChallengeSolvedEvent;
import com.microservices.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BronzeBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedEvent solvedDTO) {
        return currentScore > 50 ?
                Optional.of(BadgeType.BRONZE) :
                Optional.empty();
    }

    @Override
    public BadgeType getBadgeType() {
        return BadgeType.BRONZE;
    }
}
