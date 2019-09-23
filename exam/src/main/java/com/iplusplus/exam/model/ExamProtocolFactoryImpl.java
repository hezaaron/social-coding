package com.iplusplus.exam.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.iplusplus.exam.entity.Exam;
import com.iplusplus.exam.entity.ExamProtocol;

@Component
public class ExamProtocolFactoryImpl implements ExamProtocolFactory {

	@Override
	public ExamProtocol createInstance(Exam exam, LocalDateTime startTime, Integer questionCount) {
		return new ExamProtocol(exam, startTime, questionCount);
	}

}
