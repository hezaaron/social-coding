package com.iplusplus.gamification.service;

import com.iplusplus.gamification.model.GameStats;

public interface GameService {
	GameStats newAttemptForUser(String username, Long examId, boolean pass);
	GameStats retrieveStatsForUser(String username);
}
