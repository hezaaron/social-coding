package com.iplusplus.exam.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.iplusplus.exam.dto.ExamDTO;
import com.iplusplus.exam.entity.Answer;
import com.iplusplus.exam.entity.Exam;
import com.iplusplus.exam.entity.ExamProtocol;
import com.iplusplus.exam.entity.Question;
import com.iplusplus.exam.model.ExamProtocolFactory;
import com.iplusplus.exam.model.ExamTime;
import com.iplusplus.exam.model.ExamTimeFactory;
import com.iplusplus.exam.repository.AnswerRepository;
import com.iplusplus.exam.repository.ExamProtocolRepository;
import com.iplusplus.exam.repository.ExamRepository;
import com.iplusplus.exam.repository.QuestionRepository;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

class ExamServiceImplTest {

	private ExamServiceImpl examService;
	@Mock private ExamRepository examRepository;
	@Mock private QuestionRepository questionRepository;
	@Mock private AnswerRepository answerRepository;
	@Mock private ExamProtocolRepository protocolRepository;
	@Mock private ExamProtocolFactory protocolFactory;
	@Mock private ExamTimeFactory timeFactory;
	@Mock private HttpServletRequest request;
	@Mock private HttpSession session;
	@Mock private ExamTime examTime;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		FixtureFactoryLoader.loadTemplates("com.iplusplus.exam.template");
		examService = new ExamServiceImpl(examRepository, questionRepository, answerRepository,
									  	  protocolRepository, protocolFactory, timeFactory);
	}
	
	@Test
    void testFindsAllExams() {
		List<Exam> fixtureExams = Fixture.from(Exam.class).gimme(3, "valid");
		given(examRepository.findAll()).willReturn(fixtureExams);
        final List<Exam> exams = examService.getAllExams();
        assertAll("exams",
        		() -> assertFalse(exams.isEmpty()),
        		() -> assertEquals(3, exams.size()),
        		() -> assertNotNull(exams.get(1).getName()));
    }

	@Test
	void testGetExamDetails() {
		Exam fixtureExam = Fixture.from(Exam.class).gimme("valid");
		List<Question> fixtureQuestions = Fixture.from(Question.class).gimme(5, "valid");
		ExamProtocol fixtureProtocol = Fixture.from(ExamProtocol.class).gimme("valid");
		int remainingTime = 60;
		
		given(examRepository.getOne(any(Integer.class))).willReturn(fixtureExam);
		given(questionRepository.findByExamId(any(Integer.class))).willReturn(fixtureQuestions);
		given(request.getSession()).willReturn(session);
		given(protocolFactory.createInstance(any(), any(), any())).willReturn(fixtureProtocol);
		given(timeFactory.createInstance()).willReturn(examTime);
		given(examTime.getRemainingTime(request)).willReturn(remainingTime);
		
		final Map<String, Object> model = examService.getExamDetails(fixtureExam.getId(), request);
		
		assertAll("model",
				() -> assertNotNull(model),
				() -> assertEquals(model.get("name"), fixtureExam.getName()),
				() -> assertEquals(model.get("timer"), remainingTime),
				() -> assertTrue(model.get("result") instanceof ExamDTO));
	}
	
	@Test
	void testGetQuestionsForExam() {
		List<Question> fixtureQuestions = Fixture.from(Question.class).gimme(5, "valid");
		given(questionRepository.findByExamId(anyInt())).willReturn(fixtureQuestions);
		int examId = fixtureQuestions.get(0).getExam().getId();
		final List<Question> randomQuestions = examService.getQuestionsForExam(examId);
		assertAll("randomQuestions",
				() -> assertNotNull(randomQuestions),
				() -> assertFalse(randomQuestions.isEmpty()),
				() -> assertEquals(5, randomQuestions.size()));
	}
	
	@Test
	void testGetAnswersForExam() {
		List<Answer> fixtureAnswers = Fixture.from(Answer.class).gimme(4, "valid");
		given(answerRepository.findByQuestionExamId(any(Integer.class))).willReturn(fixtureAnswers);
		int examId = fixtureAnswers.get(0).getQuestion().getExam().getId();
		final List<Answer> answers = examService.getAnswersForExam(examId);
		assertAll("answers",
				() -> assertNotNull(answers),
				() -> assertFalse(answers.isEmpty()),
				() -> assertEquals(4, answers.size()));
	}
}