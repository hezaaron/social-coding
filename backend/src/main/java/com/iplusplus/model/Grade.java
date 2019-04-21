package com.iplusplus.model;

import java.util.Collections;
import java.util.List;

import com.iplusplus.entity.ExamResult;

public final class Grade {

	private ExamResult examResult;
	private final List<Integer> correctAnswers;
	private final List<Integer> userAnswers;
	
	public Grade(ExamResult examResult, List<Integer> correctAnswers, List<Integer> userAnswers) {
		this.examResult = examResult;
		this.correctAnswers = Collections.unmodifiableList(correctAnswers);
		this.userAnswers = Collections.unmodifiableList(userAnswers);
	}
	
	public void computeGrade() {
        long count = userAnswers.stream().filter(id -> correctAnswers.contains(id))
			        					 .map(id -> id)
			        					 .count();
        float score = count * Mark.getMarkPerQuestion();
        examResult.setCorrectAnswers((int)count);
        examResult.setGrade(Math.round(score));
	}
}