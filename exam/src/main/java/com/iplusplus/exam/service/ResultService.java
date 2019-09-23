package com.iplusplus.exam.service;

import java.util.List;
import java.util.Map;

import com.iplusplus.exam.entity.ExamProtocol;

public interface ResultService {

	ExamProtocol updateExamProtocol(ExamProtocol protocol);
	void computeGrade(ExamProtocol protocol, Integer examId, List<Long> userAnswers, String user);
	Map<String,Object> getExamStats(final Long protocolId);
	ExamProtocol getExamProtocol(Long protocolId);
}
