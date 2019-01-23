package com.iplusplus.model;

import java.util.ArrayList;
import java.util.List;

import com.iplusplus.entity.ExamResult;

public final class Grade {

	private ExamResult examResult;
	private List<Integer> correctAnswers;
	private List<Integer> userAnswers;
	
	public Grade(ExamResult examResult, List<Integer> correctAnswers, List<Integer> userAnswers) {
		this.examResult = examResult;
		this.correctAnswers = correctAnswers;
		this.userAnswers = userAnswers;
	}
	
	public void computeGrade() {
        final List<Integer> correctUserAnswers = new ArrayList<>();
        final float mark = Mark.getMarkPerQuestion();
        float score = 0;
        for(final Integer id: userAnswers) {
        	if(correctAnswers.contains(id)) {
        		score += mark;
        		correctUserAnswers.add(id);
        	}
        }
        examResult.setCorrectAnswers(correctUserAnswers.size());
        examResult.setGrade(Math.round(score));
	}
}