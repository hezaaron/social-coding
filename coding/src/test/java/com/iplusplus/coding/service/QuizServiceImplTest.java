package com.iplusplus.coding.service;

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

import com.iplusplus.coding.dto.QuizDTO;
import com.iplusplus.coding.entity.Answer;
import com.iplusplus.coding.entity.Quiz;
import com.iplusplus.coding.entity.Protocol;
import com.iplusplus.coding.entity.Question;
import com.iplusplus.coding.model.ProtocolFactory;
import com.iplusplus.coding.model.QuizTime;
import com.iplusplus.coding.model.QuizTimeFactory;
import com.iplusplus.coding.repository.AnswerRepository;
import com.iplusplus.coding.repository.ProtocolRepository;
import com.iplusplus.coding.repository.QuizRepository;
import com.iplusplus.coding.repository.QuestionRepository;
import com.iplusplus.coding.service.QuizServiceImpl;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

public class QuizServiceImplTest {

	private QuizService quizService;
	@Mock private QuizRepository quizRepository;
	@Mock private QuestionRepository questionRepository;
	@Mock private AnswerRepository answerRepository;
	@Mock private ProtocolRepository protocolRepository;
	@Mock private ProtocolFactory protocolFactory;
	@Mock private QuizTimeFactory timeFactory;
	@Mock private HttpServletRequest request;
	@Mock private HttpSession session;
	@Mock private QuizTime quizTime;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		FixtureFactoryLoader.loadTemplates("com.iplusplus.coding.template");
		quizService = new QuizServiceImpl(quizRepository, questionRepository, answerRepository,
									  	  protocolRepository, protocolFactory, timeFactory);
	}
	
	@Test
    void testFindsAllExams() {
		List<Quiz> fixtureExams = Fixture.from(Quiz.class).gimme(3, "valid");
		given(quizRepository.findAll()).willReturn(fixtureExams);
        final List<Quiz> quizzes = quizService.getAllQuizzes();
        assertAll("quizzes",
        		() -> assertFalse(quizzes.isEmpty()),
        		() -> assertEquals(3, quizzes.size()),
        		() -> assertNotNull(quizzes.get(1).getName()));
    }

	@Test
	void testGetExamDetails() {
		Quiz fixtureQuiz = Fixture.from(Quiz.class).gimme("valid");
		List<Question> fixtureQuestions = Fixture.from(Question.class).gimme(5, "valid");
		Protocol fixtureProtocol = Fixture.from(Protocol.class).gimme("valid");
		int remainingTime = 60;
		
		given(quizRepository.getOne(any(Integer.class))).willReturn(fixtureQuiz);
		given(questionRepository.findByQuizId(any(Integer.class))).willReturn(fixtureQuestions);
		given(request.getSession()).willReturn(session);
		given(protocolFactory.createInstance(any(), any(), any())).willReturn(fixtureProtocol);
		given(timeFactory.createInstance()).willReturn(quizTime);
		given(quizTime.getRemainingTime(request)).willReturn(remainingTime);
		
		final Map<String, Object> model = quizService.getQuizDetails(fixtureQuiz.getId(), request);
		
		assertAll("model",
				() -> assertNotNull(model),
				() -> assertEquals(model.get("name"), fixtureQuiz.getName()),
				() -> assertEquals(model.get("timer"), remainingTime),
				() -> assertTrue(model.get("result") instanceof QuizDTO));
	}
	
	@Test
	void testGetQuestionsForExam() {
		List<Question> fixtureQuestions = Fixture.from(Question.class).gimme(5, "valid");
		given(questionRepository.findByQuizId(anyInt())).willReturn(fixtureQuestions);
		int examId = fixtureQuestions.get(0).getQuiz().getId();
		final List<Question> randomQuestions = quizService.getQuestionsForQuiz(examId);
		assertAll("randomQuestions",
				() -> assertNotNull(randomQuestions),
				() -> assertFalse(randomQuestions.isEmpty()),
				() -> assertEquals(5, randomQuestions.size()));
	}
	
	@Test
	void testGetAnswersForExam() {
		List<Answer> fixtureAnswers = Fixture.from(Answer.class).gimme(4, "valid");
		given(answerRepository.findByQuestionQuizId(any(Integer.class))).willReturn(fixtureAnswers);
		Integer quizId = fixtureAnswers.get(0).getQuestion().getQuiz().getId();
		final List<Answer> answers = quizService.getAnswersForQuiz(quizId);
		assertAll("answers",
				() -> assertNotNull(answers),
				() -> assertFalse(answers.isEmpty()),
				() -> assertEquals(4, answers.size()));
	}
}