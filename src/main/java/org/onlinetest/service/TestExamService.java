package org.onlinetest.service;

import java.util.List;

import org.onlinetest.entity.Question;
import org.onlinetest.entity.QuestionChoice;
import org.onlinetest.entity.TestExam;

public interface TestExamService {

	TestExam findExamByName(String name);
	String getExamName(int examId);
	String getExamDescription(int examId);
	List<Question> getQuestions(int examId);
	List<QuestionChoice> getQuestionChoices(int examId);
	List<QuestionChoice> getCorrectChoices(int examId, boolean isCorrect);
}
