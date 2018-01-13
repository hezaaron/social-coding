package org.onlinetest.dao;

import org.onlinetest.entity.ExamPractice;

public interface ExamPracticeDao {

	ExamPractice saveExam(ExamPractice ExamPractice);
	int saveAndGetId(ExamPractice examPractice);
	ExamPractice updateExam(ExamPractice examPractice);
	ExamPractice getExamPractice(int id);
		
}