package com.iplusplus.exam.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.iplusplus.exam.entity.Answer;
import com.iplusplus.exam.entity.Exam;
import com.iplusplus.exam.entity.Question;

public interface ExamService {

	List<Exam> getAllExams();
	Map<String, Object> getExamDetails(Integer examId, HttpServletRequest request);
	List<Question> getQuestionsForExam(Integer examId);
	List<Answer> getAnswersForExam(Integer examId);
}
