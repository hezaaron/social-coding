package com.iplusplus.gamification.model;

import java.time.Clock;
import java.time.LocalDateTime;

import lombok.Getter;

public final class BagTime {

	private final Clock clock;
	@Getter
	private final LocalDateTime time;
	
	public BagTime(final Clock clock) {
		this.clock = clock;
		this.time = LocalDateTime.now(this.clock);
	}
}
