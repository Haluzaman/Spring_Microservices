package com.microservices.gamification.game.data;

import com.microservices.gamification.game.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeRepository extends CrudRepository<BadgeCard, Long> {

    /**
     * Retrieves all Badgecards for a given user
     *
     * @param userId the id of the user to look for BadgeCards.
     * @return the list of BadgeCards, ordered by most recent first.
     * */
    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(Long userId);

}
