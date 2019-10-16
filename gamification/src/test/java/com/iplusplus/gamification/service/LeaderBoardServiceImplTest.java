package com.iplusplus.gamification.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.iplusplus.gamification.model.LeaderBoardRow;
import com.iplusplus.gamification.repository.ScoreCardRepository;

public class LeaderBoardServiceImplTest {

	private LeaderBoardService leaderBoardService;
	@Mock private ScoreCardRepository scoreCardRepository;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		leaderBoardService = new LeaderBoardServiceImpl(scoreCardRepository);
	}
	
	@Test
	public void testRetrieveLeaderBoard() {
		String username = "hezaaron";
		LeaderBoardRow leaderBoardRow = new LeaderBoardRow(username, 300L);
		List<LeaderBoardRow> expectedLeaderBoardRows = Collections.singletonList(leaderBoardRow);
		
		given(scoreCardRepository.findFirst10()).willReturn(expectedLeaderBoardRows);
		
		List<LeaderBoardRow> leaderBoardRows = leaderBoardService.getCurrentLeaderBoard();
		
		assertEquals(expectedLeaderBoardRows, leaderBoardRows);
	}
}
