package com.iplusplus.gamification.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.iplusplus.gamification.entity.BadgeCard;
import com.iplusplus.gamification.entity.ScoreCard;
import com.iplusplus.gamification.model.Badge;
import com.iplusplus.gamification.model.GameStats;
import com.iplusplus.gamification.repository.BadgeCardRepository;
import com.iplusplus.gamification.repository.ScoreCardRepository;

public class GameServiceImplTest {

	private GameService gameService;
	@Mock private ScoreCardRepository scoreCardRepository;
	@Mock private BadgeCardRepository badgeCardRepository;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		gameService = new GameServiceImpl(scoreCardRepository, badgeCardRepository);
	}
	
	@Test
	public void testFirstAttemptBadge() {
		String username = "hezaaron";
		int score = 10;
		ScoreCard scoreCard = new ScoreCard(username, score);
		
		given(scoreCardRepository.getTotalScoreForUser(username)).willReturn(score);
		given(scoreCardRepository.findByUsernameOrderByScoreTimeDesc(username))
								 .willReturn(Collections.singletonList(scoreCard));
		given(badgeCardRepository.findByUsernameOrderByBadgeTimeDesc(username))
								 .willReturn(Collections.emptyList());
		
		GameStats stats = gameService.newAttemptForUser(username, score);
		
		assertAll("stats",
				() -> assertEquals(10, stats.getScore()),
				() -> assertEquals(1, stats.getBadges().size()),
				() -> assertTrue(stats.getBadges().contains(Badge.FIRST_WON)));
	}
	
	@Test
	public void testPassedAttemptForScoreBadge() {
		String username = "hezaaron";
		int totalScore = 100;
		
		BadgeCard firstWonBadge = new BadgeCard(username, Badge.FIRST_WON);
		given(scoreCardRepository.getTotalScoreForUser(username))
								 .willReturn(totalScore);
		given(scoreCardRepository.findByUsernameOrderByScoreTimeDesc(username))
								 .willReturn(createNScoreCards(10, username));
		given(badgeCardRepository.findByUsernameOrderByBadgeTimeDesc(username))
								 .willReturn(Collections.singletonList(firstWonBadge));
		
		GameStats stats = gameService.newAttemptForUser(username, totalScore);
		
		assertEquals(Badge.BRONZE, stats.getBadges().get(0));
	}

	private List<ScoreCard> createNScoreCards(int n, String username) {
		return IntStream.range(0, n)
					   .mapToObj(i -> new ScoreCard(username, i))
					   .collect(Collectors.toList());
	}
	
	@Test
	public void testFailedAttempt() {
		String username = "hezaaron";
		int totalScore = 20;
		ScoreCard scoreCard = new ScoreCard(username, totalScore);
		
		given(scoreCardRepository.getTotalScoreForUser(username))
								.willReturn(totalScore);
		given(scoreCardRepository.findByUsernameOrderByScoreTimeDesc(username))
								.willReturn(Collections.singletonList(scoreCard));
		given(badgeCardRepository.findByUsernameOrderByBadgeTimeDesc(username))
								.willReturn(Collections.emptyList());
		
		GameStats stats = gameService.newAttemptForUser(username, totalScore);
		
		assertAll("stats",
				() -> assertEquals(20, stats.getScore()),
				() -> assertTrue(stats.getBadges().size() == 1));
	}
	
	@Test
	public void testRetrieveStatsForUser() {
		String username = "hezaaron";
		int totalScore = 1000;
		BadgeCard badgeCard = new BadgeCard(username, Badge.SILVER);
		
		given(scoreCardRepository.getTotalScoreForUser(username))
								.willReturn(totalScore);
		given(badgeCardRepository.findByUsernameOrderByBadgeTimeDesc(username))
								.willReturn(Collections.singletonList(badgeCard));
		
		GameStats stats = gameService.retrieveStatsForUser(username);
		
		assertAll("stats",
				() -> assertEquals(totalScore, stats.getScore()),
				() -> assertTrue(stats.getBadges().contains(Badge.SILVER)));
	}
}
