package socialcoding.controller;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import socialcoding.dto.QuizDTO;
import socialcoding.entity.Answer;
import socialcoding.entity.Question;
import socialcoding.entity.Quiz;
import socialcoding.model.QuizSetter;
import socialcoding.service.QuizService;

@ContextConfiguration(classes = QuizController.class)
@WebMvcTest @WithMockUser
public class QuizControllerTest {

	@MockBean private QuizService quizService;
	@Autowired private MockMvc mockMvc;
	@Mock private HttpServletRequest request;
	@Mock private HttpSession session;
	
	private JacksonTester<List<Quiz>> jsonQuizzes;
	private JacksonTester<QuizSetter> jsonQuizSetter;
	private JacksonTester<List<Question>> jsonQuestions;
	private JacksonTester<List<Answer>> jsonAnswers;
	
	@BeforeEach
	void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
		FixtureFactoryLoader.loadTemplates("socialcoding.template");
	}

	@Test
	void testGetAllQuizzes() throws Exception {
		List<Quiz> quizzes = Fixture.from(Quiz.class).gimme(5, "valid");
		given(quizService.getAllQuiz()).willReturn(quizzes);
		
		MockHttpServletResponse response = mockMvc.perform(get("/quizzes"))
												  .andReturn().getResponse();
		assertAll("quizzes",
					() -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
					() -> assertEquals(jsonQuizzes.write(quizzes).getJson(), response.getContentAsString()));		
	}
	
	@Test
	void testSetQuiz() throws Exception {
		Quiz quiz = Fixture.from(Quiz.class).gimme("valid");
		QuizDTO quizDto = Fixture.from(QuizDTO.class).gimme("valid");
		int remainingTime = 60;
		QuizSetter quizSetter = new QuizSetter(quiz.getName(), remainingTime, quizDto);
		given(request.getSession()).willReturn(session);
		given(quizService.setQuiz(anyInt(), any())).willReturn(quizSetter);
		
		MockHttpServletResponse response = mockMvc.perform(get("/quizzes/1/quiz-setter"))
				  								  .andReturn().getResponse();
		assertAll("quizDetails",
				() -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
				() -> assertEquals(jsonQuizSetter.write(quizSetter).getJson(), response.getContentAsString()));
	}
	
	@Test
	void testGetQuestion() throws Exception {
		List<Question> questions = Fixture.from(Question.class).gimme(5, "valid");
		given(quizService.getQuestions(anyInt())).willReturn(questions);
		
		MockHttpServletResponse response = mockMvc.perform(get("/quizzes/1/questions"))
						  						  .andReturn().getResponse();
		assertAll("questions",
		() -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
		() -> assertEquals(jsonQuestions.write(questions).getJson(), response.getContentAsString()));
	}
	
	@Test
	void testGetAnswers() throws Exception {
		List<Answer> answers = Fixture.from(Answer.class).gimme(4, "valid");
		given(quizService.getAnswers(anyInt())).willReturn(answers);
		
		MockHttpServletResponse response = mockMvc.perform(get("/quizzes/1/answers"))
						  						  .andReturn().getResponse();
		assertAll("answers",
					() -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
					() -> assertEquals(jsonAnswers.write(answers).getJson(), response.getContentAsString()));
	}
}