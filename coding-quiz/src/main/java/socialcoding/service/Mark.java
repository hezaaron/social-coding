package socialcoding.service;

import java.util.Collections;
import java.util.List;

import socialcoding.entity.QuizAttempt;

final class Mark implements Grade {

	static final int MAX_MARK = 100;
	static final int NUMBER_OF_QUESTION = 5;
	static final int PASS_MARK = 50;
	
	private final QuizAttempt quizAttempt;
	private final List<Long> answers;
	private final List<Long> userAnswers;
	
	public Mark(final QuizAttempt quizAttempt, final List<Long> answers, final List<Long> userAnswers, final String user) {
		this.quizAttempt = quizAttempt;
		this.answers = Collections.unmodifiableList(answers);
		this.userAnswers = Collections.unmodifiableList(userAnswers);
		this.quizAttempt.setUser(user);
	}
	
	@Override
	public void compute() {
		int correctAnswer = (int) userAnswers.stream()
					                         .filter(answers::contains)
					                         .count();
		float score = correctAnswer * (float) MAX_MARK / NUMBER_OF_QUESTION;
		this.quizAttempt.setCorrectAnswers(correctAnswer);
		this.quizAttempt.setGrade(Math.round(score));
	}
}
