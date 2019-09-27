package com.iplusplus.coding.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.iplusplus.coding.entity.Protocol;
import com.iplusplus.coding.model.Grade;

class GradeTest {
	
	@Test
	void testComputeGrade() {
		Protocol result = new Protocol();
		List<Long>correctAnswers = Arrays.asList(1L, 4L, 5L, 7L, 10L);
		List<Long>userAnswers = Arrays.asList(1L, 9L, 3L, 7L, 1L);
		String user = "hez";
		Grade grade = new Grade(result, correctAnswers, userAnswers, user);
		grade.computeGrade();
		assertAll("result",
				() -> assertEquals(3, (int) result.getCorrectAnswers()),
				() -> assertEquals(60, (int) result.getGrade()));
	}
}
