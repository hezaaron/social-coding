package com.iplusplus.coding.service;

import java.util.List;
import java.util.Map;

import com.iplusplus.coding.entity.Protocol;

public interface ResultService {

	Protocol updateExamProtocol(Protocol protocol);
	void computeGrade(Protocol protocol, Integer examId, List<Long> userAnswers, String user);
	Map<String,Object> getQuizStats(final Long protocolId);
	Protocol getExamProtocol(Long protocolId);
}
