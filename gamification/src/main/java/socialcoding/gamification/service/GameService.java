package socialcoding.gamification.service;

import socialcoding.gamification.model.GameStats;

public interface GameService {
	GameStats newAttemptForUser(String username, int score);
	GameStats retrieveStatsForUser(String username);
}
