package com.iplusplus.gamification.service;

import java.util.List;

import com.iplusplus.gamification.model.LeaderBoardRow;

public interface LeaderBoardService {

	List<LeaderBoardRow> getCurrentLeaderBoard();
}
