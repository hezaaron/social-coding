package socialcoding.model;

import lombok.Getter;
import socialcoding.dto.QuizDTO;

@Getter
public final class QuizSetter {

	private final String quizName;
	private final int timer;
	private final QuizDTO quizDto;
	
	public QuizSetter(final String quizName, final int timer, final QuizDTO quizDto) {
		this.quizName = quizName;
		this.timer = timer;
		this.quizDto = quizDto;
	}
}
