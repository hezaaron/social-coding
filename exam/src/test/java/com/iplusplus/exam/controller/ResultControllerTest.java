package com.iplusplus.exam.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iplusplus.exam.dto.ExamDTO;
import com.iplusplus.exam.entity.ExamProtocol;
import com.iplusplus.exam.model.Mark;
import com.iplusplus.exam.service.ResultServiceImpl;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@AutoConfigureMockMvc(addFilters=false)
@ContextConfiguration(classes=ResultController.class)
@WebMvcTest @WithMockUser
public class ResultControllerTest {
	
	@MockBean private ResultServiceImpl resultService;
	@Autowired private MockMvc mockMvc;
	@Mock private HttpServletRequest request;
	private JacksonTester<ExamDTO> jsonExamDto;
	private JacksonTester<Map<String, Object>> jsonStats;
	
	@BeforeEach
	void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
		FixtureFactoryLoader.loadTemplates("com.iplusplus.exam.template");
	}

	@Test
	void testSubmitResult() throws Exception {
		ExamProtocol protocol = Fixture.from(ExamProtocol.class).gimme("valid");
		ExamDTO examDto = Fixture.from(ExamDTO.class).gimme("valid");
		
		given(resultService.getExamProtocol(anyLong())).willReturn(protocol);
		doNothing().when(resultService).computeGrade(any(), anyInt(), any(), anyString());
		given(resultService.updateExamProtocol(any())).willReturn(protocol);
		
		MockHttpServletResponse response = mockMvc.perform(post("/results")
												  .contentType(MediaType.APPLICATION_JSON)
												  .content(jsonExamDto.write(examDto).getJson()))
												  .andReturn().getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	void testGetExamStats() throws Exception {
		ExamProtocol protocol = Fixture.from(ExamProtocol.class).gimme("valid");
		Map<String, Object> stats = new HashMap<>();
		stats.put("title", String.format("Your result for %s", protocol.getExam().getName()));
		stats.put("startTime", protocol.getStartTime().toLocalTime().toString());
		stats.put("finishTime", protocol.getFinishTime().toLocalTime().toString());
		stats.put("questionCount", protocol.getQuestionCount());
		stats.put("grade", protocol.getGrade());
		stats.put("maxGrade", Mark.MAX_MARK);

		given(resultService.getExamStats(anyLong())).willReturn(stats);
		
		MockHttpServletResponse response = mockMvc.perform(get("/results/1"))
												  .andReturn().getResponse();
		assertAll("results",
					() -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
					() -> assertEquals(jsonStats.write(stats).getJson(), response.getContentAsString()));
	}
}
