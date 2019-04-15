package com.iplusplus.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.iplusplus.entity.Question;

public final class RandomQuestion {

	private final List<Question> questions;
	private final int size;
	
	public RandomQuestion(List<Question> questions, int size) {
		this.questions = Collections.unmodifiableList(questions);
		this.size = size;
	}
	
	public List<Question> getList() {
    	final List<Question> randomQuestions = new ArrayList<>();
    	for(int i = 1; i <= size; i++) {
    		int id = getRandomId();
    		questions.stream().filter(question -> question.getId() == id)
    						  .findAny()
    						  .ifPresent(randomQuestions::add);
    	}
        return randomQuestions;
	}
	
	private Integer getRandomId() {
		final int minValue = questions.get(0).getId();
    	final int maxValue = questions.get(questions.size() - 1).getId();
    	return new Random().nextInt((maxValue - minValue) + 1) + minValue; 
	}
}
