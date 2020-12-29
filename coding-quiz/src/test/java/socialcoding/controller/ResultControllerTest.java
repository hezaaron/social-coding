package socialcoding.controller;

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

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import socialcoding.dto.QuizDTO;
import socialcoding.entity.QuizAttempt;
import socialcoding.model.QuizReport;
import socialcoding.service.ResultService;

@AutoConfigureMockMvc(addFilters=false)
@ContextConfiguration(classes=ResultController.class)
@WebMvcTest @WithMockUser
public class ResultControllerTest {
	
	@MockBean private ResultService resultService;
	@Autowired private MockMvc mockMvc;
	@Mock private HttpServletRequest request;
	private JacksonTester<QuizDTO> jsonQuizDto;
	private JacksonTester<QuizReport> jsonQuizReport;
	
	@BeforeEach
	void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
		FixtureFactoryLoader.loadTemplates("socialcoding.template");
	}

	@Test
	void testSubmitAnswer() throws Exception {
		QuizAttempt quizAttempt = Fixture.from(QuizAttempt.class).gimme("valid");
		QuizDTO quizDto = Fixture.from(QuizDTO.class).gimme("valid");
		
		given(resultService.getQuizAttempt(anyLong())).willReturn(quizAttempt);
		doNothing().when(resultService).computeGrade(any(), anyInt(), any(), anyString());
		given(resultService.updateQuizAttempt(any())).willReturn(quizAttempt);
		
		MockHttpServletResponse response = mockMvc.perform(post("/results")
												  .contentType(MediaType.APPLICATION_JSON)
												  .content(jsonQuizDto.write(quizDto).getJson()))
												  .andReturn().getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	void testGetResult() throws Exception {
		QuizAttempt quizAttempt = Fixture.from(QuizAttempt.class).gimme("valid");
		String title = String.format("Your result for %s", quizAttempt.getQuiz().getName());
		String startTime = quizAttempt.getStartTime().toLocalTime().toString();
		String finishTime =  quizAttempt.getFinishTime().toLocalTime().toString();
		int questionCount = quizAttempt.getQuestionCount();
		int grade = quizAttempt.getGrade();
		int maxGrade = 100;
		QuizReport quizReport = new QuizReport(title, startTime, finishTime, questionCount, grade, maxGrade);

		given(resultService.getResult(anyLong())).willReturn(quizReport);
		
		MockHttpServletResponse response = mockMvc.perform(get("/results/1"))
												  .andReturn().getResponse();
		assertAll("quizStats",
					() -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
					() -> assertEquals(jsonQuizReport.write(quizReport).getJson(), response.getContentAsString()));
	}
}