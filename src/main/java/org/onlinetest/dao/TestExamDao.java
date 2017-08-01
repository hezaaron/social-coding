package org.onlinetest.dao;

import java.util.Hashtable;

import org.onlinetest.model.TestExam;
import org.onlinetest.model.User;

public interface TestExamDao {

	public TestExam[] loadNewExam(User user);
	public void saveExamAnswer(User user, Hashtable<Integer, String> answer, int totalScore);
	public void saveExamScore(User user, int totalScore);
}