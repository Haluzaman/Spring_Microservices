package com.microservices.gamification.game.domain;

import java.util.List;

public interface LeaderBoardService {

    /**
     * @return the current leader board ranked from high to low score
     * */
    List<LeaderBoardRow> getCurrentLeaderBoard();

}
