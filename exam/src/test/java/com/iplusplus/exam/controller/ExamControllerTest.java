package com.iplusplus.exam.controller;


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
import com.iplusplus.exam.dto.ExamDTO;
import com.iplusplus.exam.entity.Answer;
import com.iplusplus.exam.entity.Exam;
import com.iplusplus.exam.entity.Question;
import com.iplusplus.exam.service.ExamServiceImpl;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@ContextConfiguration(classes=ExamController.class)
@WebMvcTest @WithMockUser
public class ExamControllerTest {

	@MockBean private ExamServiceImpl examService;
	@Autowired private MockMvc mockMvc;
	@Mock private HttpServletRequest request;
	@Mock private HttpSession session;
	
	private JacksonTester<List<Exam>> jsonExams;
	private JacksonTester<Map<String, Object>> jsonModel;
	private JacksonTester<List<Question>> jsonQuestions;
	private JacksonTester<List<Answer>> jsonAnswers;
	
	@BeforeEach
	void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
		FixtureFactoryLoader.loadTemplates("com.iplusplus.exam.template");
	}

	@Test
	void testGetAllExams() throws Exception {
		List<Exam> exams = Fixture.from(Exam.class).gimme(5, "valid");
		given(examService.getAllExams()).willReturn(exams);
		MockHttpServletResponse response = mockMvc.perform(get("/exams"))
												  .andReturn().getResponse();
		assertAll("exams",
					() -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
					() -> assertEquals(response.getContentAsString(), jsonExams.write(exams).getJson()));		
	}
	
	@Test
	void testGetExamDetails() throws Exception {
		Exam exam = Fixture.from(Exam.class).gimme("valid");
		ExamDTO examDto = Fixture.from(ExamDTO.class).gimme("valid");
		int remainingTime = 60;
		
		Map<String, Object> model = new HashMap<>();
		model.put("name", exam.getName());
		model.put("timer", remainingTime);
		model.put("result", examDto);
	
		given(request.getSession()).willReturn(session);
		given(examService.getExamDetails(anyInt(), any())).willReturn(model);
		
		MockHttpServletResponse response = mockMvc.perform(get("/exams/exam-details/1"))
				  								  .andReturn().getResponse();
		assertAll("model",
				() -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
				() -> assertEquals(response.getContentAsString(), jsonModel.write(model).getJson()));
	}
	
	@Test
	void testGetQuestionForExam() throws Exception {
		List<Question> questions = Fixture.from(Question.class).gimme(5, "valid");
		given(examService.getQuestionsForExam(anyInt())).willReturn(questions);
		
		MockHttpServletResponse response = mockMvc.perform(get("/exams/exam-questions/1"))
						  						  .andReturn().getResponse();
		assertAll("questions",
		() -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
		() -> assertEquals(response.getContentAsString(), jsonQuestions.write(questions).getJson()));
	}
	
	@Test
	void testGetAnswersForExam() throws Exception {
		List<Answer> answers = Fixture.from(Answer.class).gimme(4, "valid");
		given(examService.getAnswersForExam(anyInt())).willReturn(answers);
		
		MockHttpServletResponse response = mockMvc.perform(get("/exams/exam-answers/1"))
						  						  .andReturn().getResponse();
		assertAll("answers",
					() -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
					() -> assertEquals(response.getContentAsString(), jsonAnswers.write(answers).getJson()));
	}
}
