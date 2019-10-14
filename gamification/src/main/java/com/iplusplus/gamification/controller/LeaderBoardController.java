package com.iplusplus.gamification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplusplus.gamification.model.LeaderBoardRow;
import com.iplusplus.gamification.service.LeaderBoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/leaders")
@RequiredArgsConstructor
public class LeaderBoardController {

	private final LeaderBoardService leaderBoardService;
	
	@GetMapping
	public List<LeaderBoardRow> getLeaderBoard() {
		return leaderBoardService.getCurrentLeaderBoard();
	}
}
