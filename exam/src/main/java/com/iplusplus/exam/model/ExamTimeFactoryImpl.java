package com.iplusplus.exam.model;

import org.springframework.stereotype.Component;

@Component
public class ExamTimeFactoryImpl implements ExamTimeFactory {

	@Override
	public ExamTime createInstance() {
		return new ExamTime();
	}

}
