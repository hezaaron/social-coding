package com.iplusplus.coding.model;

import org.springframework.stereotype.Component;

@Component
public class QuizTimeFactoryImpl implements QuizTimeFactory {

	@Override
	public QuizTime createInstance() {
		return new QuizTime();
	}

}
