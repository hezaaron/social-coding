package com.iplusplus.exam.repository;

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

import com.iplusplus.exam.entity.Answer;
import com.iplusplus.exam.entity.Exam;
import com.iplusplus.exam.entity.ExamProtocol;
import com.iplusplus.exam.entity.Question;
import com.iplusplus.exam.repository.AnswerRepository;
import com.iplusplus.exam.repository.ExamRepository;
import com.iplusplus.exam.repository.ExamProtocolRepository;
import com.iplusplus.exam.repository.QuestionRepository;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RepositoryIntegrationTests {

	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private ExamProtocolRepository examResultRepository;
	private Exam fixtureExam;

	@BeforeEach
	void setUp() throws Exception {
		FixtureFactoryLoader.loadTemplates("com.iplusplus.exam.template");
		fixtureExam = Fixture.from(Exam.class).gimme("valid");
	}

	@AfterEach
	void tearDown() throws Exception {
		examResultRepository.deleteAll();
		answerRepository.deleteAll();
		questionRepository.deleteAll();
		examRepository.deleteAll();
	}

	@Test
	void shouldSaveAndFetchExam() {
		examRepository.save(fixtureExam);
		List<Exam> exams = examRepository.findAll();
		assertAll("exams", () -> assertFalse(exams.isEmpty()), () -> assertEquals(1, exams.size()),
				() -> assertTrue(exams.contains(fixtureExam)));
	}

	@Test
	void shouldSaveAndFetchQuestion() {
		examRepository.save(fixtureExam);
		Question fixtureQuestion = Fixture.from(Question.class).gimme("valid");
		questionRepository.save(fixtureQuestion);
		List<Question> questions = questionRepository.findAll();
		assertAll("questions", () -> assertFalse(questions.isEmpty()), () -> assertEquals(1, questions.size()));
	}

	@Test
	void shouldSaveAndFetchAnswers() {
		examRepository.save(fixtureExam);
		Question fixtureQuestion = Fixture.from(Question.class).gimme("valid");
		questionRepository.save(fixtureQuestion);
		Answer fixtureAnswer = Fixture.from(Answer.class).gimme("valid");
		answerRepository.save(fixtureAnswer);
		Long questionId = fixtureAnswer.getQuestion().getId();
		List<Answer> answers = answerRepository.findByQuestionId(questionId);
		assertAll("answers", () -> assertFalse(answers.isEmpty()), () -> assertEquals(1, answers.size()));
	}

	@Test
	void shouldSaveAndFetchCorrectAnswer() {
		examRepository.save(fixtureExam);
		Question fixtureQuestion = Fixture.from(Question.class).gimme("valid");
		questionRepository.save(fixtureQuestion);
		Answer fixtureAnswer = Fixture.from(Answer.class).gimme("valid");
		answerRepository.save(fixtureAnswer);
		List<Answer> correctAnswers = answerRepository.findByQuestionExamIdAndCorrect(fixtureExam.getId(),
				fixtureAnswer.isCorrect());
		assertAll("correctAnswers", () -> assertFalse(correctAnswers.isEmpty()),
				() -> assertEquals(1, correctAnswers.size()));
	}

	@Test
	void shouldSaveAndFetchExamResult() {
		examRepository.save(fixtureExam);
		ExamProtocol fixtureExamResult = Fixture.from(ExamProtocol.class).gimme("valid");
		examResultRepository.save(fixtureExamResult);
		List<ExamProtocol> examResults = examResultRepository.findAll();
		assertAll("examResults", () -> assertFalse(examResults.isEmpty()), () -> assertEquals(1, examResults.size()));
	}
}
