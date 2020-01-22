package com.iplusplus.coding.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.iplusplus.coding.entity.Answer;
import com.iplusplus.coding.entity.Quiz;
import com.iplusplus.coding.entity.Protocol;
import com.iplusplus.coding.entity.Question;
import com.iplusplus.coding.repository.AnswerRepository;
import com.iplusplus.coding.repository.ProtocolRepository;
import com.iplusplus.coding.repository.QuizRepository;
import com.iplusplus.coding.repository.QuestionRepository;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RepositoryIntegrationTests {

	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private ProtocolRepository protocolRepository;
	private Quiz fixtureQuiz;

	@BeforeEach
	void setUp() throws Exception {
		FixtureFactoryLoader.loadTemplates("com.iplusplus.coding.template");
		fixtureQuiz = Fixture.from(Quiz.class).gimme("valid");
	}

	@AfterEach
	void tearDown() throws Exception {
		protocolRepository.deleteAll();
		answerRepository.deleteAll();
		questionRepository.deleteAll();
		quizRepository.deleteAll();
	}

	@Test
	void shouldSaveAndFetchQuiz() {
		quizRepository.save(fixtureQuiz);
		
		List<Quiz> quizzes = quizRepository.findAll();
		assertAll("quizzes",
					() -> assertFalse(quizzes.isEmpty()), () -> assertEquals(1, quizzes.size()),
					() -> assertTrue(quizzes.contains(fixtureQuiz)));
	}

	@Test
	void shouldSaveAndFetchQuestion() {
		quizRepository.save(fixtureQuiz);
		Question fixtureQuestion = Fixture.from(Question.class).gimme("valid");
		questionRepository.save(fixtureQuestion);
		
		List<Question> questions = questionRepository.findAll();
		assertAll("questions",
					() -> assertFalse(questions.isEmpty()),
					() -> assertEquals(1, questions.size()));
	}

	@Test
	void shouldSaveAndFetchAnswers() {
		quizRepository.save(fixtureQuiz);
		Question fixtureQuestion = Fixture.from(Question.class).gimme("valid");
		questionRepository.save(fixtureQuestion);
		Answer fixtureAnswer = Fixture.from(Answer.class).gimme("valid");
		answerRepository.save(fixtureAnswer);
		Long questionId = fixtureAnswer.getQuestion().getId();
		
		List<Answer> answers = answerRepository.findByQuestionId(questionId);
		assertAll("answers",
					() -> assertFalse(answers.isEmpty()),
					() -> assertEquals(1, answers.size()));
	}

	@Test
	void shouldSaveAndFetchCorrectAnswer() {
		quizRepository.save(fixtureQuiz);
		Question fixtureQuestion = Fixture.from(Question.class).gimme("valid");
		questionRepository.save(fixtureQuestion);
		Answer fixtureAnswer = Fixture.from(Answer.class).gimme("valid");
		answerRepository.save(fixtureAnswer);
		
		List<Answer> correctAnswers = answerRepository.findByQuestionQuizIdAndCorrect(fixtureQuiz.getId(),
				fixtureAnswer.isCorrect());
		
		assertAll("correctAnswers",
					() -> assertFalse(correctAnswers.isEmpty()),
					() -> assertEquals(1, correctAnswers.size()));
	}

	@Test
	void shouldSaveAndFetchProtocol() {
		quizRepository.save(fixtureQuiz);
		Protocol fixtureProtocol = Fixture.from(Protocol.class).gimme("valid");
		protocolRepository.save(fixtureProtocol);
		
		List<Protocol> protocols = protocolRepository.findAll();
		
		assertAll("protocols",
					() -> assertFalse(protocols.isEmpty()),
					() -> assertEquals(1, protocols.size()));
	}
}
