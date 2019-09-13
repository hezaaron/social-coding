package com.iplusplus.exam.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iplusplus.exam.controller.ResultController;
import com.iplusplus.exam.dto.ExamResultDTO;
import com.iplusplus.exam.entity.ExamResult;
import com.iplusplus.exam.model.Mark;
import com.iplusplus.exam.service.ExamService;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

public class ResultControllerApiTests {
	
	private MockMvc mockMvc;
	private ResultController resultController;
	@Mock private ExamService examService;
	@Mock private HttpServletRequest request;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		FixtureFactoryLoader.loadTemplates("com.iplusplus.exam.template");
		resultController = new ResultController(examService);
		mockMvc = MockMvcBuilders.standaloneSetup(resultController).build();
	}

	@Test
	void testSubmitResult() throws Exception {
		ExamResult result = Fixture.from(ExamResult.class).gimme("valid");
		int id = 1, examId = result.getExam().getId();
		ExamResultDTO resultDto = new ExamResultDTO(id, examId);
		List<Integer> userAnswers = Arrays.asList(2, 4, 56, 34, 70);
		String userName = result.getUser();
		resultDto.setAnswers(userAnswers);
		resultDto.setUserName(userName);
		given(examService.getExamResult(anyInt())).willReturn(result);
		doNothing().when(examService).computeGrade(any(), anyInt(), any(), anyString());
		given(examService.updateExamResult(any())).willReturn(result);
		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(resultDto);
		
		mockMvc.perform(post("/results").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isOk());
		verify(examService, times(1)).getExamResult(anyInt());
		verify(examService, times(1)).computeGrade(any(), anyInt(), any(), anyString());
		verify(examService, times(1)).updateExamResult(any());
		verifyNoMoreInteractions(examService);
	}
	
	@Test
	void testGetExamStats() throws Exception {
		ExamResult result = Fixture.from(ExamResult.class).gimme("valid");
		Map<String, Object> stats = new HashMap<>();
		stats.put("title", String.format("Your result for %s", result.getExam().getName()));
		stats.put("startTime", result.getStartTime().toLocalTime().toString());
		stats.put("finishTime", result.getFinishTime().toLocalTime().toString());
		stats.put("questionCount", result.getQuestionCount());
		stats.put("grade", result.getGrade());
		stats.put("maxGrade", Mark.MAX_MARK);
		int resultId = result.getId();
		given(examService.getExamStats(resultId)).willReturn(stats);
		mockMvc.perform(get("/results/{resultId}", resultId, request).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isMap())
				.andExpect(jsonPath("$.title").value(Matchers.any(String.class)))
				.andExpect(jsonPath("$.questionCount").value(5))
				.andExpect(jsonPath("$.grade").value(Matchers.any(Integer.class)))
				.andExpect(jsonPath("$.maxGrade").value(100));
		verify(examService, times(1)).getExamStats(anyInt());
		verifyNoMoreInteractions(examService);
	}
}
