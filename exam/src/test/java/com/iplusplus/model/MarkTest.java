package com.iplusplus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MarkTest {

	@Test
	void testGetMarkPerQuestion() {
		float mark = Mark.getMarkPerQuestion();
		assertEquals(20.0, mark);
	}

}
