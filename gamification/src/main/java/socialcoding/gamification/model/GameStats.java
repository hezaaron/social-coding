package socialcoding.gamification.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class GameStats {

	private final String username;
	private final int score;
	private final List<Badge> badges;
	
	public GameStats() {
		this(null, 0, new ArrayList<>());
	}
	
	public static GameStats emptyStats(final String username) {
		return new GameStats(username, 0, Collections.emptyList());
	}
	
	public List<Badge> getBadges() {
		return Collections.unmodifiableList(badges);
	}
}
