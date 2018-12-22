package com.iplusplus.model;

import org.springframework.stereotype.Component;

import com.iplusplus.dto.ExamResultDTO;
import com.iplusplus.entity.ExamResult;

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
