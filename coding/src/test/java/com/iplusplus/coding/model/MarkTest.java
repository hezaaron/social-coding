package com.iplusplus.coding.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.iplusplus.coding.model.Mark;

class MarkTest {

	@Test
	void testGetMarkPerQuestion() {
		float mark = Mark.getMarkPerQuestion();
		assertEquals(20.0, mark);
	}

}
