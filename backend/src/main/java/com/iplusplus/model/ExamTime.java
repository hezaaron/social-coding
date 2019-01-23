package com.iplusplus.model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExamTime {

	private Clock clock;
	@Getter
	private LocalDateTime time;

	public ExamTime(Clock clock) {
		this.clock = clock;
		this.time = LocalDateTime.now(this.clock);
	}
	
	public int getRemainingTime(HttpServletRequest request) {
		int examTimeInMinute = 1;
		LocalDateTime start = (LocalDateTime) (request.getSession().getAttribute("examStarted"));
		final long startInMillis = start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return  (int) ((examTimeInMinute * 60) - ((Calendar.getInstance().getTimeInMillis() - startInMillis) / 1000));
	}
}
