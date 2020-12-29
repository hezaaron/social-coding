package socialcoding.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import socialcoding.entity.Answer;
import socialcoding.entity.Question;
import socialcoding.entity.Quiz;
import socialcoding.repository.AnswerRepository;
import socialcoding.repository.QuestionRepository;
import socialcoding.repository.QuizAttemptRepository;
import socialcoding.repository.QuizRepository;

public class QuizServiceImplTest {

	private QuizService quizService;
	@Mock private QuizRepository quizRepository;
	@Mock private QuestionRepository questionRepository;
	@Mock private AnswerRepository answerRepository;
	@Mock private QuizAttemptRepository quizAttemptRepository;
	@Mock private HttpServletRequest request;
	@Mock private HttpSession session;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		FixtureFactoryLoader.loadTemplates("socialcoding.template");
		quizService = new QuizServiceImpl(quizRepository, questionRepository, answerRepository,
									  	  quizAttemptRepository);
	}
	
	@Test
    void testFindsAllQuizzes() {
		List<Quiz> fixtureQuizzes = Fixture.from(Quiz.class).gimme(3, "valid");
		given(quizRepository.findAll()).willReturn(fixtureQuizzes);
        
		final List<Quiz> quizzes = quizService.getAllQuiz();
        
		assertAll("quizzes",
	        		() -> assertFalse(quizzes.isEmpty()),
	        		() -> assertEquals(3, quizzes.size()),
	        		() -> assertNotNull(quizzes.get(1).getName()));
    }
	
	@Test
	void testGetQuestions() {
		List<Question> questions = Fixture.from(Question.class).gimme(5, "valid");
		given(questionRepository.findByQuizId(anyInt())).willReturn(questions);
		int quizId = questions.get(0).getQuiz().getId();
		
		final List<Question> randomQuestions = quizService.getQuestions(quizId);
		
		assertAll("randomQuestions",
					() -> assertNotNull(randomQuestions),
					() -> assertFalse(randomQuestions.isEmpty()),
					() -> assertEquals(5, randomQuestions.size()));
	}
	
	@Test
	void testGetAnswersForQuiz() {
		List<Answer> fixtureAnswers = Fixture.from(Answer.class).gimme(4, "valid");
		given(answerRepository.findByQuestionQuizId(any(Integer.class))).willReturn(fixtureAnswers);
		Integer quizId = fixtureAnswers.get(0).getQuestion().getQuiz().getId();
		
		final List<Answer> answers = quizService.getAnswers(quizId);
		
		assertAll("answers",
					() -> assertNotNull(answers),
					() -> assertFalse(answers.isEmpty()),
					() -> assertEquals(4, answers.size()));
	}
}