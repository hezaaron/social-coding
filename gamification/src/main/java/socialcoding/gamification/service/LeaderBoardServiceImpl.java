package socialcoding.gamification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import socialcoding.gamification.model.LeaderBoardRow;
import socialcoding.gamification.repository.ScoreCardRepository;

@Service
@RequiredArgsConstructor
public class LeaderBoardServiceImpl implements LeaderBoardService {

	private final ScoreCardRepository scoreCardRepository;
	
	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}

}
