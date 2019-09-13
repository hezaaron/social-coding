package com.iplusplus.exam.model;

import org.springframework.stereotype.Component;

import com.iplusplus.exam.dto.ExamResultDTO;
import com.iplusplus.exam.entity.ExamResult;

@Component
public class FactoryHelper {

	public ExamTime makeExamTime() {
		return new ExamTime();
	}
	
	public ExamResult makeExamResult() {
		return new ExamResult();
	}
	
	public ExamResultDTO makeExamResultDto(Integer id, Integer examId) {
		return new ExamResultDTO(id, examId);
	}
}
