package com.iplusplus.coding.model;

import java.time.LocalDateTime;

import com.iplusplus.coding.entity.Quiz;
import com.iplusplus.coding.entity.Protocol;

public interface ProtocolFactory {

	Protocol createInstance(Quiz quiz, LocalDateTime startTime, Integer questionCount);
}
