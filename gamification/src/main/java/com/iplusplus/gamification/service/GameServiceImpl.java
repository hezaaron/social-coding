package com.iplusplus.gamification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.iplusplus.gamification.entity.BadgeCard;
import com.iplusplus.gamification.entity.ScoreCard;
import com.iplusplus.gamification.model.Badge;
import com.iplusplus.gamification.model.GameStats;
import com.iplusplus.gamification.repository.BadgeCardRepository;
import com.iplusplus.gamification.repository.ScoreCardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor @Slf4j
public class GameServiceImpl implements GameService {

	private final ScoreCardRepository scoreCardRepository;
	private final BadgeCardRepository badgeCardRepository;
	
	@Override
	public GameStats newAttemptForUser(final String username, final Long quizId, boolean pass) {
		if(pass) {
			ScoreCard scoreCard = new ScoreCard(username, quizId);
			scoreCardRepository.save(scoreCard);
			log.info("User {} scored {} points for exam id {}", username, scoreCard.getScore(), quizId);
			List<BadgeCard> badgeCards = processForBadges(username);
			
			return new GameStats(username, scoreCard.getScore(),
								badgeCards.stream().map(BadgeCard::getBadge)
										  .collect(Collectors.toList()));
		}
		
		return GameStats.emptyStats(username);
	}
	
	private List<BadgeCard> processForBadges(final String username) {
		List<BadgeCard> badgeCards = new ArrayList<>();
		int totalScore = scoreCardRepository.getTotalScoreForUser(username);
		log.info("New score for user {} is {}", username, totalScore);
		List<ScoreCard> scoreCardList = scoreCardRepository.findByUsernameOrderByScoreTimeDesc(username);
		List<BadgeCard> badgeCardList = badgeCardRepository.findByUsernameOrderByBadgeTimeDesc(username);
		chackAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE, totalScore, 100, username)
									.ifPresent(badgeCards::add);
		chackAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER, totalScore, 500, username)
									.ifPresent(badgeCards::add);
		chackAndGiveBadgeBasedOnScore(badgeCardList, Badge.GOLD, totalScore, 999, username)
									.ifPresent(badgeCards::add);
		log.info(String.valueOf(scoreCardList));
		if(scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_WON)) {
			BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, username);
			badgeCards.add(firstWonBadge);
		}
		return badgeCards;
	}
	
	private Optional<BadgeCard> chackAndGiveBadgeBasedOnScore(final List<BadgeCard> badgeCards,
					final Badge badge, final int score, final int scoreThreshold, final String username) {
		if(score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
			return Optional.of(giveBadgeToUser(badge, username));
		}
		return Optional.empty();
	}
	
	private boolean containsBadge(final List<BadgeCard> badgeCards, final Badge badge) {
		return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));
	}
	
	private BadgeCard giveBadgeToUser(final Badge badge, final String username) {
		BadgeCard badgeCard = new BadgeCard(username, badge);
		badgeCardRepository.save(badgeCard);
		log.info("User {} won a new badge: {}", username, badge);
		return badgeCard;
	}

	@Override
	public GameStats retrieveStatsForUser(final String username) {
		int score = scoreCardRepository.getTotalScoreForUser(username);
		List<BadgeCard> badgeCards = badgeCardRepository.findByUsernameOrderByBadgeTimeDesc(username);
		return new GameStats(username, score, badgeCards.stream()
														.map(BadgeCard::getBadge)
														.collect(Collectors.toList()));
	}

}