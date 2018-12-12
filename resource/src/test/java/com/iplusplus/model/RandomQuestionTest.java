package com.iplusplus.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.iplusplus.entity.Question;

class RandomQuestionTest {

	@Test
	void testGetList() {
		Question question1 = new Question();
		question1.setId(1);
		question1.setName("What is inheritance");
		Question question2 = new Question();
		question2.setId(2);
		question2.setName("what is composition");
		Question question3 = new Question();
		question3.setId(3);
		question3.setName("what is polymophism");
		Question question4 = new Question();
		question4.setId(4);
		question4.setName("what is abstraction");
		List<Question> questions = Arrays.asList(question1, question2, question3, question4);
		int size = 2;
		RandomQuestion randomQuestion = new RandomQuestion(questions, size);
		
		List<Question> randomQuestions = randomQuestion.getList();
		
		assertAll("randomQuestions",
				() -> assertFalse(randomQuestions.isEmpty()),
				() -> assertEquals(2, randomQuestions.size()));
	}
}