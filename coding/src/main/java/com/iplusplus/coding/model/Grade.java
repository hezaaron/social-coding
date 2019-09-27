package com.iplusplus.coding.model;

import java.util.Collections;
import java.util.List;

import com.iplusplus.coding.entity.Protocol;

public final class Grade {

	private final Protocol examResult;
	private final List<Long> correctAnswers;
	private final List<Long> userAnswers;
	private final String user;
	
	public Grade(Protocol examResult, List<Long> correctAnswers, List<Long> userAnswers, String user) {
		this.examResult = examResult;
		this.correctAnswers = Collections.unmodifiableList(correctAnswers);
		this.userAnswers = Collections.unmodifiableList(userAnswers);
		this.user = user;
	}
	
	public void computeGrade() {
        int correctUserAnswer = (int) userAnswers.stream().filter(correctAnswers::contains)
								        				  .count();
        float score = correctUserAnswer * Mark.getMarkPerQuestion();
        examResult.setUser(user);
        examResult.setCorrectAnswers(correctUserAnswer);
        examResult.setGrade(Math.round(score));
	}
}