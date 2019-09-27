package com.iplusplus.coding.controller;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.iplusplus.coding.controller.QuizController;
import com.iplusplus.coding.dto.QuizDTO;
import com.iplusplus.coding.entity.Answer;
import com.iplusplus.coding.entity.Quiz;
import com.iplusplus.coding.entity.Question;
import com.iplusplus.coding.service.QuizServiceImpl;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@ContextConfiguration(classes=QuizController.class)
@WebMvcTest @WithMockUser
public class QuizControllerTest {

	@MockBean private QuizServiceImpl quizService;
	@Autowired private MockMvc mockMvc;
	@Mock private HttpServletRequest request;
	@Mock private HttpSession session;
	
	private JacksonTester<List<Quiz>> jsonExams;
	private JacksonTester<Map<String, Object>> jsonModel;
	private JacksonTester<List<Question>> jsonQuestions;
	private JacksonTester<List<Answer>> jsonAnswers;
	
	@BeforeEach
	void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
		FixtureFactoryLoader.loadTemplates("com.iplusplus.coding.template");
	}

	@Test
	void testGetAllExams() throws Exception {
		List<Quiz> quizs = Fixture.from(Quiz.class).gimme(5, "valid");
		given(quizService.getAllQuizzes()).willReturn(quizs);
		MockHttpServletResponse response = mockMvc.perform(get("/quizzes"))
												  .andReturn().getResponse();
		assertAll("exams",
					() -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
					() -> assertEquals(response.getContentAsString(), jsonExams.write(quizs).getJson()));		
	}
	
	@Test
	void testGetQuizDetails() throws Exception {
		Quiz quiz = Fixture.from(Quiz.class).gimme("valid");
		QuizDTO examDto = Fixture.from(QuizDTO.class).gimme("valid");
		int remainingTime = 60;
		
		Map<String, Object> model = new HashMap<>();
		model.put("name", quiz.getName());
		model.put("timer", remainingTime);
		model.put("result", examDto);
	
		given(request.getSession()).willReturn(session);
		given(quizService.getQuizDetails(anyInt(), any())).willReturn(model);
		
		MockHttpServletResponse response = mockMvc.perform(get("/quizzes/details/1"))
				  								  .andReturn().getResponse();
		assertAll("model",
				() -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
				() -> assertEquals(response.getContentAsString(), jsonModel.write(model).getJson()));
	}
	
	@Test
	void testGetQuestionForQuiz() throws Exception {
		List<Question> questions = Fixture.from(Question.class).gimme(5, "valid");
		given(quizService.getQuestionsForQuiz(anyInt())).willReturn(questions);
		
		MockHttpServletResponse response = mockMvc.perform(get("/quizzes/questions/1"))
						  						  .andReturn().getResponse();
		assertAll("questions",
		() -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
		() -> assertEquals(response.getContentAsString(), jsonQuestions.write(questions).getJson()));
	}
	
	@Test
	void testGetAnswersForQuiz() throws Exception {
		List<Answer> answers = Fixture.from(Answer.class).gimme(4, "valid");
		given(quizService.getAnswersForQuiz(anyInt())).willReturn(answers);
		
		MockHttpServletResponse response = mockMvc.perform(get("/quizzes/answers/1"))
						  						  .andReturn().getResponse();
		assertAll("answers",
					() -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
					() -> assertEquals(response.getContentAsString(), jsonAnswers.write(answers).getJson()));
	}
}
