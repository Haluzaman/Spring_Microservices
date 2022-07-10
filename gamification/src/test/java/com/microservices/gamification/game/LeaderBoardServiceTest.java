package com.microservices.gamification.game;

import com.microservices.gamification.game.data.BadgeRepository;
import com.microservices.gamification.game.data.ScoreRepository;
import com.microservices.gamification.game.domain.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class LeaderBoardServiceTest {

    private LeaderBoardService leaderBoardService;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private BadgeRepository badgeRepository;

    @BeforeEach
    public void setUp() {
        this.leaderBoardService = new LeaderBoardServiceImpl(scoreRepository, badgeRepository);
    }

    @Test
    public void retrieveLeaderBoardTest() {
        // given
        LeaderBoardRow scoreRow = new LeaderBoardRow(1L, 300L, List.of());
        given(scoreRepository.findFirst10()).willReturn(List.of(scoreRow));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(1L))
                .willReturn(List.of(new BadgeCard(1L, BadgeType.LUCKY_NUMBER)));

        // when
        List<LeaderBoardRow> leaderBoard = leaderBoardService.getCurrentLeaderBoard();

        // then
        List<LeaderBoardRow> expectedLeaderBoard = List.of(
                new LeaderBoardRow(1L, 300L,
                        List.of(BadgeType.LUCKY_NUMBER.getDescription()))
        );
        then(leaderBoard).isEqualTo(expectedLeaderBoard);
    }
}
