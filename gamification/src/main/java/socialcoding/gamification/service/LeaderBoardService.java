package socialcoding.gamification.service;

import java.util.List;

import socialcoding.gamification.model.LeaderBoardRow;

public interface LeaderBoardService {

	List<LeaderBoardRow> getCurrentLeaderBoard();
}
