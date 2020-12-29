package socialcoding.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import socialcoding.entity.Answer;
import socialcoding.entity.Question;
import socialcoding.entity.Quiz;
import socialcoding.model.QuizSetter;

public interface QuizService {

	List<Quiz> getAllQuiz();
	QuizSetter setQuiz(Integer id, HttpServletRequest request);
	Quiz getQuiz(Integer id);
	List<Question> getQuestions(Integer quizId);
	List<Answer> getAnswers(Integer quizId);
}