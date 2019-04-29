package com.iplusplus.model;

import java.util.Collections;
import java.util.List;

import com.iplusplus.entity.ExamResult;

public final class Grade {

	private final ExamResult examResult;
	private final List<Integer> correctAnswers;
	private final List<Integer> userAnswers;
	
	public Grade(ExamResult examResult, List<Integer> correctAnswers, List<Integer> userAnswers) {
		this.examResult = examResult;
		this.correctAnswers = Collections.unmodifiableList(correctAnswers);
		this.userAnswers = Collections.unmodifiableList(userAnswers);
	}
	
	public void computeGrade() {
        Integer correctUserAnswer = (int) userAnswers.stream().filter(answer -> correctAnswers.contains(answer))
								        					  .mapToInt(answer -> answer)
								        					  .count();
        float score = correctUserAnswer * Mark.getMarkPerQuestion();
        examResult.setCorrectAnswers(correctUserAnswer);
        examResult.setGrade(Math.round(score));
	}
}