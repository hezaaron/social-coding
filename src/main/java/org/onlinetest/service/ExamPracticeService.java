package org.onlinetest.service;

import java.util.List;

import org.onlinetest.entity.ExamPractice;

public interface ExamPracticeService {

	ExamPractice saveExam(ExamPractice examPractice);
	int saveAndGetId(ExamPractice examPractice);
	ExamPractice update(ExamPractice examPractice);
	ExamPractice getExamPractice(int id);
	void calculateGrade(ExamPractice examPractice, int examId, List<Integer> userChoices);
}
