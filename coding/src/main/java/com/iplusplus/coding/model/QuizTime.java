package com.iplusplus.coding.model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuizTime {

	private final Clock clock;
	@Getter
	private final LocalDateTime time;

	public QuizTime() {
		this(null, null);
	}
	public QuizTime(Clock clock) {
		this.clock = clock;
		this.time = LocalDateTime.now(this.clock);
	}
	
	public int getRemainingTime(HttpServletRequest request) {
		int examTimeInMinute = 1;
		LocalDateTime start = (LocalDateTime) (request.getSession().getAttribute("quizStarted"));
		final long startInMillis = start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return  (int) ((examTimeInMinute * 60) - ((Calendar.getInstance().getTimeInMillis() - startInMillis) / 1000));
	}
}
