package com.iplusplus.coding.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.iplusplus.coding.entity.Question;

public final class RandomQuestion {

	private final List<Question> questions;
	private final int size;
	
	public RandomQuestion(List<Question> questions, int size) {
		this.questions = Collections.unmodifiableList(questions);
		this.size = size;
	}
	
	public List<Question> getList() {
    	final List<Question> randomQuestions = new ArrayList<>(size);
    	for(int i = 1; i <= size; i++) {
    		final long id = getRandomQuestionId();
    		questions.stream().filter(question -> question.getId() == id)
    						  .findAny()
    						  .ifPresent(randomQuestions::add);
    	}
        return randomQuestions;
	}
	
	private long getRandomQuestionId() {
		final Long minValue = questions.get(0).getId();
    	final Long maxValue = questions.get(questions.size() - 1).getId();
    	return (long) (Math.random() * (maxValue - minValue)) + minValue; 
	}
}
