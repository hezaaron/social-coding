package socialcoding.gamification.entity;

import java.time.Clock;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import socialcoding.gamification.model.BagTime;

@Entity
@Getter @ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class ScoreCard {

	public static final int DEFAULT_SCORE = 10;
	@Id @GeneratedValue
	private final Long id;
	private final String username;
	private final LocalDateTime scoreTime;
	private final int score;
	
	public ScoreCard() {
		this(null, null, null, 0);
	}
	
	public ScoreCard(final String username, final int score) {
		this(null, username, new BagTime(Clock.systemDefaultZone()).getTime(), score);
	}
}
