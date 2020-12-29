package socialcoding.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import socialcoding.entity.QuizAttempt;
import socialcoding.event.EventDispatcher;
import socialcoding.event.QuizSolvedEvent;
import socialcoding.model.QuizReport;
import socialcoding.repository.AnswerRepository;
import socialcoding.repository.QuizAttemptRepository;

public class ResultServiceImplTest {

	private ResultService resultService;
	@Mock private QuizAttemptRepository quizAttemptRepository;
	@Mock private AnswerRepository answerRepository;
    @Mock private EventDispatcher eventDispatcher;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		FixtureFactoryLoader.loadTemplates("socialcoding.template");
		resultService = new ResultServiceImpl(quizAttemptRepository, answerRepository, eventDispatcher);
	}
	
	@Test
	void testGetQuizAttempt() {
		QuizAttempt fixtureQuizAttempt = Fixture.from(QuizAttempt.class).gimme("valid");
		given(quizAttemptRepository.getOne(anyLong())).willReturn(fixtureQuizAttempt);
		Long quizAttemptId = fixtureQuizAttempt.getId();
		
		QuizAttempt quizAttempt = resultService.getQuizAttempt(quizAttemptId);
		
		assertAll("quizAttempt",
					() -> assertNotNull(quizAttempt),
					() -> assertEquals(5, (int)quizAttempt.getQuestionCount()),
					() -> assertNotNull(quizAttempt.getGrade()));
	}
	
	@Test
	void testUpdateQuizAttempt() {
		QuizAttempt fixtureQuizAttempt = Fixture.from(QuizAttempt.class).gimme("valid");
		QuizSolvedEvent event = new QuizSolvedEvent(fixtureQuizAttempt.getUser(), fixtureQuizAttempt.getGrade());
		
		given(quizAttemptRepository.save(any())).willReturn(fixtureQuizAttempt);
		QuizAttempt quizAttempt = resultService.updateQuizAttempt(fixtureQuizAttempt);
		
		assertAll("protocol",
					() -> assertNotNull(quizAttempt),
					() -> assertNotNull(quizAttempt.getQuiz()));
		verify(eventDispatcher).send(eq(event));
	}
	
	@Test
	void testGetResult() {
		QuizAttempt quizAttempt = Fixture.from(QuizAttempt.class).gimme("valid");
		given(quizAttemptRepository.getOne(anyLong())).willReturn(quizAttempt);
		
		QuizReport quizReport = resultService.getResult(quizAttempt.getId());
		
		assertAll("QuizReport",
				() -> assertNotNull(quizReport),
				() -> assertEquals(5, quizReport.getQuestionCount()));
	}
}
