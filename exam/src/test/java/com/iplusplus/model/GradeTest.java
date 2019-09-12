package com.iplusplus.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.iplusplus.entity.ExamResult;

class GradeTest {
	
	@Test
	void testComputeGrade() {
		ExamResult result = new ExamResult();
		List<Integer>correctAnswers = Arrays.asList(1, 4, 5, 7, 10);
		List<Integer>userAnswers = Arrays.asList(1, 9, 3, 7, 1 );
		String user = "hez";
		Grade grade = new Grade(result, correctAnswers, userAnswers, user);
		grade.computeGrade();
		assertAll("result",
				() -> assertEquals(3, (int)result.getCorrectAnswers()),
				() -> assertEquals(60, (int)result.getGrade()));
	}
}
