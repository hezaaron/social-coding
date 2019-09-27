package com.iplusplus.coding.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.iplusplus.coding.entity.Answer;
import com.iplusplus.coding.entity.Quiz;
import com.iplusplus.coding.entity.Question;

public interface QuizService {

	List<Quiz> getAllQuizzes();
	Map<String, Object> getQuizDetails(Integer quizId, HttpServletRequest request);
	List<Question> getQuestionsForQuiz(Integer quizId);
	List<Answer> getAnswersForQuiz(Integer quizId);
}
