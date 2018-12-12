package com.iplusplus.model;

import java.time.Clock;
import java.time.LocalDateTime;

import lombok.Getter;

public class ExamTime {

	private final Clock clock;
	@Getter
	private LocalDateTime time;
	
	public ExamTime(Clock clock) {
		this.clock = clock;
		this.time = LocalDateTime.now(this.clock);
	}
}
