package socialcoding.service;

import java.util.List;

import socialcoding.entity.QuizAttempt;
import socialcoding.model.QuizReport;

public interface ResultService {

	QuizAttempt getQuizAttempt(Long quizAttemptId);
	QuizAttempt updateQuizAttempt(QuizAttempt quizAttempt);
	void computeGrade(QuizAttempt quizAttempt, Integer quizId, List<Long> userAnswers, String user);
	QuizReport getResult(Long quizAttemptId);
}
