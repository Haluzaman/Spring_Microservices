package com.microservices.gamification.game.domain;

import com.microservices.gamification.game.data.BadgeRepository;
import com.microservices.gamification.game.data.ScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class GameServiceImpl implements GameService {

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;
    //spring will inject all the @Compent beans in this list
    private final List<BadgeProcessor> badgeProcessors;

    @Autowired
    public GameServiceImpl(
            final ScoreRepository scoreRepository,
            final BadgeRepository badgeRepository,
            final List<BadgeProcessor> badgeProcessors) {
        this.scoreRepository = scoreRepository;
        this.badgeRepository = badgeRepository;
        this.badgeProcessors = badgeProcessors;
    }

    @Override
    public GameResult newAttemptForUser(final ChallengeSolvedEvent challenge) {
        if (challenge.getCorrect()) {
            ScoreCard scoreCard = new ScoreCard(challenge.getUserId(), challenge.getAttemptId());
            scoreRepository.save(scoreCard);
            log.info("User {} scored {} points", scoreCard.getScore(), challenge.getAttemptId());

            List<BadgeCard> badgeCardList = processForBages(challenge);
            return new GameResult(scoreCard.getScore(),
                                  badgeCardList.stream()
                                          .map(BadgeCard::getBadgeType)
                                          .collect(Collectors.toList())
                                );
        }

        log.info("Attempt id {} is not correct. User {} does not get score.", challenge.getAttemptId(), challenge.getUserId());
        return new GameResult(0, List.of());
    }

    private List<BadgeCard> processForBages(final ChallengeSolvedEvent solvedDTO) {
        Optional<Integer> optTotalScore = scoreRepository.getTotalScoreForUser(solvedDTO.getUserId());
        if (optTotalScore.isEmpty()) return Collections.emptyList();

        int totalScore = optTotalScore.get();
        List<ScoreCard> scoreCardList = scoreRepository.findByUserIdOrderByScoreTimestampDesc(solvedDTO.getUserId());
        Set<BadgeType> alreadyGotBadges = badgeRepository
                .findByUserIdOrderByBadgeTimestampDesc(solvedDTO.getUserId())
                .stream()
                .map(BadgeCard::getBadgeType)
                .collect(Collectors.toSet());

        List<BadgeCard> newBadgeCards = badgeProcessors.stream()
                .filter(bp -> !alreadyGotBadges.contains(bp.getBadgeType()))
                .map(bp -> bp.processForOptionalBadge(totalScore, scoreCardList, solvedDTO))
                .flatMap(Optional::stream) // returns an empty stream if empty
                .map(badgeType -> new BadgeCard(solvedDTO.getUserId(), badgeType))
                .collect(Collectors.toList());
        badgeRepository.saveAll(newBadgeCards);
        return newBadgeCards;
    }

}
