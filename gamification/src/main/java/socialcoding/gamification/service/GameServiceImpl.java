package socialcoding.gamification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import socialcoding.gamification.entity.BadgeCard;
import socialcoding.gamification.entity.ScoreCard;
import socialcoding.gamification.model.Badge;
import socialcoding.gamification.model.GameStats;
import socialcoding.gamification.repository.BadgeCardRepository;
import socialcoding.gamification.repository.ScoreCardRepository;

@Service
@RequiredArgsConstructor @Slf4j
public class GameServiceImpl implements GameService {

	private final ScoreCardRepository scoreCardRepository;
	private final BadgeCardRepository badgeCardRepository;
	
	@Override
	public GameStats newAttemptForUser(final String username, final int score) {
		int defaultScore = ScoreCard.DEFAULT_SCORE;
		ScoreCard scoreCard = (score < defaultScore) ? new ScoreCard(username, defaultScore):
													   new ScoreCard(username, score);
			
		scoreCardRepository.save(scoreCard);
		log.info("User {} scored {} points", username, scoreCard.getScore());
		List<BadgeCard> badgeCards = processForBadges(username);
		
		return (badgeCards.isEmpty()) ? GameStats.emptyStats(username) : new GameStats(username, scoreCard.getScore(),
				badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
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
		Integer score = scoreCardRepository.getTotalScoreForUser(username);
		if(score == null) score = 0;
		List<BadgeCard> badgeCards = badgeCardRepository.findByUsernameOrderByBadgeTimeDesc(username);
		return new GameStats(username, score, badgeCards.stream()
														.map(BadgeCard::getBadge)
														.collect(Collectors.toList()));
	}

}