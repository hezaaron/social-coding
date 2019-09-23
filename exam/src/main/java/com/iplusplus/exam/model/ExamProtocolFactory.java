package com.iplusplus.exam.model;

import java.time.LocalDateTime;

import com.iplusplus.exam.entity.Exam;
import com.iplusplus.exam.entity.ExamProtocol;

public interface ExamProtocolFactory {

	ExamProtocol createInstance(Exam exam, LocalDateTime startTime, Integer questionCount);
}
