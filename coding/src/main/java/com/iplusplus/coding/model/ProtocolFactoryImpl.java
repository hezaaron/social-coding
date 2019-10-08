package com.iplusplus.coding.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.iplusplus.coding.entity.Quiz;
import com.iplusplus.coding.entity.Protocol;

@Component
public class ProtocolFactoryImpl implements ProtocolFactory {

	@Override
	public Protocol createInstance(Quiz quiz, LocalDateTime startTime, Integer questionCount) {
		return new Protocol(null, quiz, startTime, questionCount);
	}

}
