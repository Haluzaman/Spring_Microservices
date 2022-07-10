//package com.example.microservices_demo.challenge;
//
//import com.example.microservices_demo.serviceclients.GamificationServiceClient;
//import com.example.microservices_demo.user.User;
//import com.example.microservices_demo.user.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.BDDAssertions.then;
//import static org.mockito.AdditionalAnswers.returnsFirstArg;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//public class ChallengeServiceTest {
//
//    private IChallengeService challengeService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private ChallengeAttemptRepository challengeAttemptRepository;
//
//    @Mock
//    private GamificationServiceClient gamificationServiceClient;
//
//    @BeforeEach
//    public void setUp() {
//        this.challengeService = new ChallengeServiceImpl(
//                userRepository,
//                challengeAttemptRepository,
//                gamificationServiceClient
//        );
//
//    }
//
//    @Test
//    public void checkCorrectAttemptTest() {
//        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());
//
//        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john_doe", 3000);
//        ChallengeAttempt resultAttempt = this.challengeService.verifyAttempt(attemptDTO);
//
//        then(resultAttempt.isCorrect()).isTrue();
//
//        verify(userRepository).save(new User("john_doe"));
//        verify(challengeAttemptRepository).save(resultAttempt);
//        verify(gamificationServiceClient).sendAttempt(resultAttempt);
//    }
//
//
//    @Test
//    public void checkWrongAttemptTest() {
//        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());
//
//        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john_doe", 5000);
//        ChallengeAttempt resultAttempt = this.challengeService.verifyAttempt(attemptDTO);
//
//        then(resultAttempt.isCorrect()).isFalse();
//        verify(gamificationServiceClient).sendAttempt(resultAttempt);
//    }
//
//    @Test
//    public void checkExistingUserTest() {
//        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());
//
//        User existingUser = new User(1L, "john_doe");
//        given(userRepository.findByAlias("john_doe")).willReturn(Optional.of(existingUser));
//
//        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john_doe", 5000);
//        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
//
//        then(resultAttempt.isCorrect()).isFalse();
//        then(resultAttempt.getUser()).isEqualTo(existingUser);
//        verify(userRepository, never()).save(any());
//        verify(challengeAttemptRepository).save(resultAttempt);
//        verify(gamificationServiceClient).sendAttempt(resultAttempt);
//    }
//
//    @Test
//    public void retrieveStatsTest() {
//        User user = new User("john_doe");
//        ChallengeAttempt attempt1 = new ChallengeAttempt(1L, user, 50, 60, 3010, false);
//        ChallengeAttempt attempt2 = new ChallengeAttempt(2L, user, 50, 60, 3051, false);
//        List<ChallengeAttempt> lastAttempts = List.of(attempt1, attempt2);
//        given(challengeAttemptRepository.findTop10ByUserAliasOrderByIdDesc("john_doe"))
//                .willReturn(lastAttempts);
//
//        List<ChallengeAttempt> latestAttemptsResult = this.challengeService.getStatsForUser("john_doe");
//
//        then(latestAttemptsResult).isEqualTo(lastAttempts);
//    }
//
//}
