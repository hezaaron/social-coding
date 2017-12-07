package org.onlinetest.service;

import java.util.Hashtable;

import org.onlinetest.entity.TestExam;
import org.onlinetest.entity.User;

public interface TestExamService {

	TestExam findExamByName(String name);
	String getExamName(int examId);
	String getExamDescription(int examId);
	TestExam[] loadNewExam(User user);
	void saveExamAnswer(User user, Hashtable<Integer, String> answer, int totalScore);
	void saveExamScore(User user, int totalScore);
}
