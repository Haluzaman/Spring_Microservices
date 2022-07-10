//package com.microservices.gamification.game.controller;
//
//import com.microservices.gamification.game.domain.ChallengeSolvedEvent;
//import com.microservices.gamification.game.domain.GameService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/attempts")
//public class GameController {
//
//    private final GameService gameService;
//
//    @Autowired
//    public GameController(GameService gameService) {
//        this.gameService = gameService;
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.OK)
//    void postResult(@RequestBody ChallengeSolvedEvent dto) {
//        gameService.newAttemptForUser(dto);
//    }
//}
