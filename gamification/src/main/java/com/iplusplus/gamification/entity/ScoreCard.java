package com.iplusplus.gamification.entity;

import java.time.Clock;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.iplusplus.gamification.model.BagTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Getter @ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class ScoreCard {

	public static final int DEFAULT_SCORE = 20;
	@Id @GeneratedValue
	private final Long id;
	private final String username;
	private final Long quizId;
	private final LocalDateTime scoreTime;
	private final int score;
	
	public ScoreCard() {
		this(null, null, null, null, 0);
	}
	
	public ScoreCard(final String username, final Long quizId) {
		this(null, username, quizId, new BagTime(Clock.systemDefaultZone()).getTime(), DEFAULT_SCORE);
	}
}
