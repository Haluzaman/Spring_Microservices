package com.microservices.gamification.game.badgeProcessors;

import com.microservices.gamification.game.domain.BadgeProcessor;
import com.microservices.gamification.game.domain.BadgeType;
import com.microservices.gamification.game.domain.ChallengeSolvedEvent;
import com.microservices.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FirstWonBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedEvent solvedDTO) {
        return scoreCardList.size() == 1 ?
                Optional.of(BadgeType.FIRST_WON) :
                Optional.empty();
    }

    @Override
    public BadgeType getBadgeType() {
        return BadgeType.FIRST_WON;
    }
}
