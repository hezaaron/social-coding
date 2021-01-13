package socialcoding.gamification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import socialcoding.gamification.model.GameStats;
import socialcoding.gamification.service.GameService;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class UserStatsController {

	private final GameService gameService;
	
	@GetMapping
	public GameStats getStatsForUser(@RequestParam("username") final String username) {
		return gameService.retrieveStatsForUser(username);
	}
}
