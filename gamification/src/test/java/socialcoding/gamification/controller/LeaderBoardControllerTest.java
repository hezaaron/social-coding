package socialcoding.gamification.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import socialcoding.gamification.model.LeaderBoardRow;
import socialcoding.gamification.service.LeaderBoardService;

@ContextConfiguration(classes = LeaderBoardController.class)
@WebMvcTest @WithMockUser
public class LeaderBoardControllerTest {

    @MockBean private LeaderBoardService leaderBoardService;
    @Autowired private MockMvc mockMvc;
    private JacksonTester<List<LeaderBoardRow>> json;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }
    
    @Test
    void testGetLeaderBoard() throws Exception {
        LeaderBoardRow leaderBoardRow1 = new LeaderBoardRow("Hez", 500L);
        LeaderBoardRow leaderBoardRow2 = new LeaderBoardRow("Aaron", 400L);
        List<LeaderBoardRow> leaderBoard = Arrays.asList(leaderBoardRow1, leaderBoardRow2);
        given(leaderBoardService.getCurrentLeaderBoard()).willReturn(leaderBoard);
        
        MockHttpServletResponse response = mockMvc.perform(get("/leaders"))
                                                  .andReturn().getResponse();
        assertAll("leaderBoard",
                        () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                        () -> assertEquals(json.write(leaderBoard).getJson(), response.getContentAsString()));
    }
}