package com.iplusplus.entity;

import java.time.Clock;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.iplusplus.model.BagTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Getter @ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public final class ScoreCard {

	public static final int DEFAULT_SCORE = 5;
	@Id @GeneratedValue
	@Column(name = "CARD_ID")
	private final Long cardId;
	@Column(name = "USER_NAME")
	private final String userName;
	@Column(name = "ATTEMPT_ID")
	private final Long attemptId;
	@Column(name = "SCORE_TIME")
	private final LocalDateTime scoreTime;
	@Column(name = "SCORE")
	private final int score;
	
	public ScoreCard() {
		this(null, null, null, null, 0);
	}
	
	public ScoreCard(final String userName, final Long attemptId) {
		this(null, userName, attemptId, new BagTime(Clock.systemDefaultZone()).getTime(), DEFAULT_SCORE);
	}
}
