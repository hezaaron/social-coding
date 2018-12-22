package com.iplusplus.model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import lombok.Getter;

public class ExamTime {

	private Clock clock;
	@Getter
	private LocalDateTime time;
	@Value("${exam.time.minutes}")
    private Integer examTimeMins;
	
	public ExamTime() {
		this(Clock.systemDefaultZone());
	}

	public ExamTime(Clock clock) {
		this.clock = clock;
		this.time = LocalDateTime.now(this.clock);
	}
	
	public int getRemainingTime(HttpServletRequest request, String startTime) {
		LocalDateTime start = (LocalDateTime) (request.getSession().getAttribute(startTime));
		final long startInMillis = start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return  (int) ((examTimeMins * 60) - ((Calendar.getInstance().getTimeInMillis() - startInMillis) / 1000));
	}
}
