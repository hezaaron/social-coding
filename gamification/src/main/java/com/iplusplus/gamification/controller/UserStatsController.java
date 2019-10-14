package com.iplusplus.gamification.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iplusplus.gamification.model.GameStats;
import com.iplusplus.gamification.service.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class UserStatsController {

	private final GameService gameService;
	
	public GameStats getStatsForUser(@RequestParam("username") final String username) {
		return gameService.retrieveStatsForUser(username);
	}
}
