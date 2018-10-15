package com.iplusplus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.iplusplus.domain.Question;
import com.iplusplus.repository.QuestionRepository;
import com.iplusplus.service.ExamService;

@RunWith(SpringRunner.class)
@Profile("test")
@TestPropertySource(locations = "classpath:/application-test.properties", inheritProperties = false)
@SpringBootTest(classes = {ResourceApplication.class})
public class ServiceTest {
	
	private static final int NUMBER_OF_QUESTIONS_PER_EXAM = 5;
	@Autowired
    ExamService examService;
	@Autowired
	QuestionRepository questionRepository;

    @Test
    public void testExamService() {
    	final List<Question> questions = questionRepository.findByExamId(2);
    	assertNotNull(questions);
    	assertTrue(questions.size() > 0);
        final List<Question> examQuestions = examService.getRandomQuestions(questions);
        assertEquals(NUMBER_OF_QUESTIONS_PER_EXAM, examQuestions.size());
        
        List<Integer> questionId = new ArrayList<>();
        for(final Question question : questions) {
        	questionId.add(question.getId());
        }
        for(Question randomQuestion : examQuestions) {
        	assertTrue(questionId.contains(randomQuestion.getId()));
        }
    }

}
