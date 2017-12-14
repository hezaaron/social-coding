package org.onlinetest.dao;

import java.util.Hashtable;
import java.util.List;

import org.onlinetest.entity.Question;
import org.onlinetest.entity.QuestionChoice;
import org.onlinetest.entity.TestExam;
import org.onlinetest.entity.User;

public interface TestExamDao {

	TestExam findExamByName(String name);
	String getExamName(int examId);
	String getExamDescription(int examId);
	List<Question> loadExam(int examId);
	List<QuestionChoice> getQuestionChoices(int examId);
	void saveExamAnswer(User user, Hashtable<Integer, String> answer, int totalScore);
	void saveExamScore(User user, int totalScore);
}