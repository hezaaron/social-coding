package socialcoding.model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class QuizTimer {

	private final Clock clock;
	@Getter private final LocalDateTime time;

	public QuizTimer() {
		this(null, null);
	}
	public QuizTimer(final Clock clock) {
		this.clock = clock;
		this.time = LocalDateTime.now(this.clock);
	}
	
	public int getRemainingTime(final HttpServletRequest request) {
		int examTimeInMinute = 1;
		LocalDateTime start = (LocalDateTime) (request.getSession().getAttribute("quizStarted"));
		final long startInMillis = start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return  (int) ((examTimeInMinute * 60) - ((Calendar.getInstance().getTimeInMillis() - startInMillis) / 1000));
	}
}
