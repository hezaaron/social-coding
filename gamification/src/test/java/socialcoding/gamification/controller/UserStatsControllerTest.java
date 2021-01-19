package socialcoding.gamification.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Collections;

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

import socialcoding.gamification.model.Badge;
import socialcoding.gamification.model.GameStats;
import socialcoding.gamification.service.GameService;

@ContextConfiguration(classes = UserStatsController.class)
@WebMvcTest @WithMockUser
public class UserStatsControllerTest {
 
    @MockBean private GameService gameService;
    @Autowired private MockMvc mockMvc;
    private JacksonTester<GameStats> json;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    void getUserStatsTest() throws Exception {
        GameStats gameStats = new GameStats("Hez", 1200, Collections.singletonList(Badge.GOLD));
        given(gameService.retrieveStatsForUser("Hez")).willReturn(gameStats);
    
        MockHttpServletResponse response = mockMvc.perform(get("/stats?username=Hez"))
                                                  .andReturn().getResponse();

        assertAll("gameStats",
                        () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                        () -> assertEquals(json.write(gameStats).getJson(), response.getContentAsString()));
    }
}
