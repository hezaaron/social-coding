package com.iplusplus.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iplusplus.entity.Question;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

class RandomQuestionTest {

	@BeforeEach
	void setUp() throws Exception {
		FixtureFactoryLoader.loadTemplates("com.iplusplus.template");
	}
	
	@Test
	void testGetList() {
		List<Question> fixtureQuestions = Fixture.from(Question.class).gimme(5, "valid");
		int size = 3;
		RandomQuestion randomQuestion = new RandomQuestion(fixtureQuestions, size);
		List<Question> randomQuestions = randomQuestion.getList();
		assertAll("randomQuestions",
				() -> assertFalse(randomQuestions.isEmpty()),
				() -> assertEquals(3, randomQuestions.size()));
	}
}