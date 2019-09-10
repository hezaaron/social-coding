package com.iplusplus.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.iplusplus.dto.ExamResultDTO;
import com.iplusplus.entity.Answer;
import com.iplusplus.entity.Exam;
import com.iplusplus.entity.ExamResult;
import com.iplusplus.entity.Question;
import com.iplusplus.model.ExamTime;
import com.iplusplus.model.FactoryHelper;
import com.iplusplus.service.ExamService;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

public class ExamControllerApiTest {

	private MockMvc mockMvc;
	private ExamController examController;
	@Mock private FactoryHelper factoryHelper;
	@Mock private ExamService examService;
	@Mock private HttpServletRequest request;
	@Mock private HttpSession session;
	@Mock private ExamTime examTime;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		FixtureFactoryLoader.loadTemplates("com.iplusplus.template");
		examController = new ExamController(examService, factoryHelper);
		mockMvc = MockMvcBuilders.standaloneSetup(examController).build();
	}

	@Test
	void testGetAllExams() throws Exception {
		List<Exam> exams = Fixture.from(Exam.class).gimme(5, "valid");
		given(examService.getAllExams()).willReturn(exams);
		mockMvc.perform(get("/exams").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").value(Matchers.hasSize(5)))
				.andExpect(jsonPath("$[0].id").value(Matchers.any(Integer.class)))
				.andExpect(jsonPath("$[0].name").value(Matchers.any(String.class)))
				.andExpect(jsonPath("$[0].description").value(Matchers.any(String.class)));
		verify(examService, times(1)).getAllExams();
		verifyNoMoreInteractions(examService);
	}
	
	@Test
	void testGetExam() throws Exception {
		Exam exam = Fixture.from(Exam.class).gimme("valid");
		ExamResult examResult = Fixture.from(ExamResult.class).gimme("valid");
		ExamResultDTO examResultDto = Fixture.from(ExamResultDTO.class).gimme("valid");
		int remainingTime = 60, examId = exam.getId();
		
		given(examService.getExam(anyInt())).willReturn(exam);
		given(factoryHelper.makeExamTime()).willReturn(examTime);
		given(examService.createExamResult(exam)).willReturn(examResult);
		given(factoryHelper.makeExamResultDto(anyInt(), anyInt())).willReturn(examResultDto);
		given(request.getSession()).willReturn(session);
		given(examTime.getRemainingTime(any())).willReturn(remainingTime);
		given(examService.getExamResultId(examResult)).willReturn(examResult.getId());

		mockMvc.perform(get("/exams/exam/{examId}", examId, request).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isMap())
				.andExpect(jsonPath("$.name").value(Matchers.any(String.class)))
				.andExpect(jsonPath("$.timer").value(60))
				.andExpect(jsonPath("$").value(Matchers.hasKey("result")))
				.andExpect(jsonPath("$.result.id").value(Matchers.any(Integer.class)))
				.andExpect(jsonPath("$.result.examId").value(Matchers.any(Integer.class)))
				.andExpect(jsonPath("$.result.answers").value(Matchers.nullValue()));
		verify(examService, times(1)).getExam(anyInt());
		verify(examService, times(1)).createExamResult(exam);
		verify(examService, times(1)).getExamResultId(examResult);
		verifyNoMoreInteractions(examService);
	}
	
	@Test
	void testGetQuestionForExam() throws Exception {
		List<Question> questions = Fixture.from(Question.class).gimme(5, "valid");
		given(examService.getQuestionsForExam(anyInt())).willReturn(questions);
		
		mockMvc.perform(get("/exams/questions/{anyInt()}", anyInt()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").value(Matchers.hasSize(5)))
				.andExpect(jsonPath("$[0].id").value(Matchers.any(Integer.class)))
				.andExpect(jsonPath("$[0].exam.id").value(Matchers.any(Integer.class)))
				.andExpect(jsonPath("$[0].name").value(Matchers.any(String.class)))
				.andExpect(jsonPath("$[0].code").value(Matchers.any(String.class)))
				.andExpect(jsonPath("$[0].multiAnswer").value(Matchers.any(Boolean.class)));
		verify(examService, times(1)).getQuestionsForExam(anyInt());
		verifyNoMoreInteractions(examService);
	}
	
	@Test
	void testGetAnswersForQuestion() throws Exception {
		List<Answer> answers = Fixture.from(Answer.class).gimme(4, "valid");
		given(examService.getAnswersForQuestion(anyInt())).willReturn(answers);
		
		mockMvc.perform(get("/exams/answers/{anyInt()}", anyInt()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").value(Matchers.hasSize(4)))
				.andExpect(jsonPath("$[0].id").value(Matchers.any(Integer.class)))
				.andExpect(jsonPath("$[0].name").value(Matchers.any(String.class)))
				.andExpect(jsonPath("$[0].question").isMap())
				.andExpect(jsonPath("$[0].correct").value(Matchers.any(Boolean.class)));
		verify(examService, times(1)).getAnswersForQuestion(anyInt());
		verifyNoMoreInteractions(examService);
	}
}
