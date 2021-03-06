package socialcoding.gamification.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter @ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class LeaderBoardRow {

	private final String username;
	private final Long totalScore;
	
	public LeaderBoardRow() {
		this(null, 0L);
	}
}
