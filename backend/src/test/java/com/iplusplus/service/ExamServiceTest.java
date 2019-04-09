package com.iplusplus.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.iplusplus.entity.Answer;
import com.iplusplus.entity.Exam;
import com.iplusplus.entity.ExamResult;
import com.iplusplus.entity.Question;
import com.iplusplus.repository.AnswerRepository;
import com.iplusplus.repository.ExamRepository;
import com.iplusplus.repository.ExamResultRepository;
import com.iplusplus.repository.QuestionRepository;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@SpringBootTest
class ExamServiceTest {

	private ExamService examService;
	@Mock ExamRepository examRepository;
	@Mock QuestionRepository questionRepository;
	@Mock AnswerRepository answerRepository;
	@Mock ExamResultRepository resultRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		FixtureFactoryLoader.loadTemplates("com.iplusplus.template");
		examService = new ExamService(examRepository, questionRepository, answerRepository, resultRepository);
	}
	
	@Test
    void testFindsAllExams() {
		List<Exam> fixtureExams = Fixture.from(Exam.class).gimme(3, "valid");
		when(examRepository.findAll()).thenReturn(fixtureExams);
        final List<Exam> exams = examService.getAllExams();
        assertAll("exams",
        		() -> assertFalse(exams.isEmpty()),
        		() -> assertEquals(3, exams.size()),
        		() -> assertNotNull(exams.get(1).getName()));
    }

	@Test
	void testGetExam() {
		Exam fixtureExam = Fixture.from(Exam.class).gimme("valid");
		when(examRepository.getOne(anyInt())).thenReturn(fixtureExam);
		int examId = fixtureExam.getId();
		final Exam exam = examService.getExam(examId);
		assertAll("exam",
				() -> assertNotNull(exam),
				() -> assertNotNull(exam.getName()),
				() -> assertEquals(exam.getName() + " exam", exam.getDescription()));
	}
	
	@Test
	void testGetQuestionsForExam() {
		List<Question> fixtureQuestions = Fixture.from(Question.class).gimme(5, "valid");
		when(questionRepository.findByExamId(anyInt())).thenReturn(fixtureQuestions);
		int examId = fixtureQuestions.get(0).getExam().getId();
		final List<Question> randomQuestions = examService.getQuestionsForExam(examId);
		assertAll("randomQuestions",
				() -> assertNotNull(randomQuestions),
				() -> assertFalse(randomQuestions.isEmpty()),
				() -> assertEquals(5, randomQuestions.size()));
	}
	
	@Test
	void testGetAnswersForQuestion() {
		List<Answer> fixtureAnswers = Fixture.from(Answer.class).gimme(4, "valid");
		when(answerRepository.findByQuestionId(anyInt())).thenReturn(fixtureAnswers);
		int questionId = fixtureAnswers.get(0).getQuestion().getId();
		final List<Answer> answers = examService.getAnswersForQuestion(questionId);
		assertAll("answers",
				() -> assertNotNull(answers),
				() -> assertFalse(answers.isEmpty()),
				() -> assertEquals(4, answers.size()));
	}
	
	@Test
	void testCreateExamResultId() {
		ExamResult fixtureResult = Fixture.from(ExamResult.class).gimme("valid");
		when(resultRepository.saveAndFlush(any())).thenReturn(fixtureResult);
		Integer savedExamResult = examService.createExamResultId(fixtureResult);
		assertEquals(fixtureResult.getId(), savedExamResult);
	}
	
	@Test
	void testUpdateExamResult() {
		ExamResult fixtureResult = Fixture.from(ExamResult.class).gimme("valid");
		when(resultRepository.save(any())).thenReturn(fixtureResult);
		ExamResult examResult = examService.updateExamResult(fixtureResult);
		assertAll("examResult",
				() -> assertNotNull(examResult),
				() -> assertNotNull(examResult.getExam()));
	}
	
	@Test
	void testGetExamResult() {
		ExamResult fixtureResult = Fixture.from(ExamResult.class).gimme("valid");
		when(resultRepository.getOne(anyInt())).thenReturn(fixtureResult);
		int resultId = fixtureResult.getId();
		ExamResult examResult = examService.getExamResult(resultId);
		assertAll("examResult",
				() -> assertNotNull(examResult),
				() -> assertEquals(5, (int)examResult.getQuestionCount()),
				() -> assertNotNull(examResult.getGrade()));
	}
	
	@Test
	void testGetExamStats() {
		ExamResult fixtureResult = Fixture.from(ExamResult.class).gimme("valid");
		when(resultRepository.getOne(anyInt())).thenReturn(fixtureResult);
		Map<String, Object> stats = examService.getExamStats(fixtureResult.getId());
		assertAll("stats",
				() -> assertFalse(stats.isEmpty()),
				() -> assertNotNull(stats.get("title")),
				() -> assertEquals(5, stats.get("questionCount")));
	}
}