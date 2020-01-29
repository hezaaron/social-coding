package com.iplusplus.gamification.service;

import com.iplusplus.gamification.model.GameStats;

public interface GameService {
	GameStats newAttemptForUser(String username, int score);
	GameStats retrieveStatsForUser(String username);
}
