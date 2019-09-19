package com.iplusplus.gamification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iplusplus.gamification.model.LeaderBoardRow;
import com.iplusplus.gamification.repository.ScoreCardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaderBoardServiceImpl implements LeaderBoardService {

	private final ScoreCardRepository scoreCardRepository;
	
	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}

}
