package com.microservices.gamification.game.domain;

import com.microservices.gamification.game.data.BadgeRepository;
import com.microservices.gamification.game.data.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    @Autowired
    public LeaderBoardServiceImpl(
            ScoreRepository scoreRepository,
            BadgeRepository badgeRepository) {
        this.scoreRepository = scoreRepository;
        this.badgeRepository = badgeRepository;
    }

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {

        List<LeaderBoardRow> scoreOnly = scoreRepository.findFirst10();
        return scoreOnly.stream().map(row -> {
            List<String> badges = badgeRepository.
                    findByUserIdOrderByBadgeTimestampDesc(row.getUserId()).stream()
                    .map(b -> b.getBadgeType().getDescription())
                    .collect(Collectors.toList());
            return row.withBadges(badges);
        }).collect(Collectors.toList());
    }
}
