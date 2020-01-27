package com.iplusplus.coding.model;

import java.util.Collections;
import java.util.List;

import com.iplusplus.coding.entity.Protocol;

public final class Grade {

	private final Protocol quizResult;
	private final List<Long> correctAnswers;
	private final List<Long> userAnswers;
	private final String user;
	
	public Grade(Protocol quizResult, List<Long> correctAnswers, List<Long> userAnswers, String user) {
		this.quizResult = quizResult;
		this.correctAnswers = Collections.unmodifiableList(correctAnswers);
		this.userAnswers = Collections.unmodifiableList(userAnswers);
		this.user = user;
	}
	
	public void compute() {
        int correctUserAnswer = (int) userAnswers.stream().filter(correctAnswers::contains)
								        				  .count();
        float score = correctUserAnswer * Mark.getMarkPerQuestion();
        quizResult.setUser(user);
        quizResult.setCorrectAnswers(correctUserAnswer);
        quizResult.setGrade(Math.round(score));
	}
}