package com.iplusplus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.iplusplus.domain.Answer;
import com.iplusplus.domain.Exam;
import com.iplusplus.domain.ExamResult;
import com.iplusplus.domain.Question;
import com.iplusplus.repository.AnswerRepository;
import com.iplusplus.repository.ExamRepository;
import com.iplusplus.repository.ExamResultRepository;
import com.iplusplus.repository.QuestionRepository;

@RunWith(SpringRunner.class)
@Profile("test")
@TestPropertySource(locations = "classpath:/application-test.properties", inheritProperties = false)
@SpringBootTest(classes = {ResourceApplication.class})
public class RepositoryTest {

	private static final int MAX_EXAMS = 3;
    private static final int MAX_QUESTIONS = 30;
    private static final int MAX_QUESTIONS_FOR_EXAM_1 = 17;
    private static final int MAX_QUESTIONS_FOR_EXAM_2 = 6;
    private static final int MAX_QUESTIONS_FOR_EXAM_3 = 7;
    private static final int MAX_ANSWERS = 120;
    private static final int MAX_ANSWERS_BY_QUESTION = 4;    
	@Autowired
	private ExamRepository examRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ExamResultRepository examResultRepository;

    @Test
    public void findsAllExams() {
        final List<Exam> list = examRepository.findAll();
        assertEquals(MAX_EXAMS, list.size());
        for(final Exam item : list) {
            assertNotNull(item.getName());
            assertTrue(item.getName().length() > 0);
        }
    }

    @Test
    public void findsAllQuestions() {
        final List<Question> list = questionRepository.findAll();
        assertEquals(MAX_QUESTIONS, list.size());
        for(final Question item : list) {
            assertNotNull(item.getName());
            assertTrue(item.getName().length() > 0);
        }
    }

    @Test
    public void findsAllAnswers() {
        final List<Answer> list = answerRepository.findAll();
        assertEquals(MAX_ANSWERS, list.size());
        for(final Answer item : list) {
            assertNotNull(item.getName());
            assertTrue(item.getName().length() > 0);
        }
    }

    @Test
    public void findsAllAnswersByExam() {
    	final List<Exam> exams = examRepository.findAll();
        assertEquals(MAX_EXAMS, exams.size());
        for(int i = 1; i <= MAX_EXAMS; i++) {
        	final List<Answer> CorrectAnswers = answerRepository.findByQuestionExamIdAndCorrect(i, true);
        	assertNotNull(CorrectAnswers);
        	final List<Answer> incorrectAnswers = answerRepository.findByQuestionExamIdAndCorrect(i, false);
        	assertNotNull(incorrectAnswers);
        	if(i == 1) {
        	    assertEquals(MAX_QUESTIONS_FOR_EXAM_1 * MAX_ANSWERS_BY_QUESTION, CorrectAnswers.size() + incorrectAnswers.size());
        	}
        	if(i == 2) {
        		assertEquals(MAX_QUESTIONS_FOR_EXAM_2 * MAX_ANSWERS_BY_QUESTION, CorrectAnswers.size() + incorrectAnswers.size());
        	}
        	if(i == 3) {
        		assertEquals(MAX_QUESTIONS_FOR_EXAM_3 * MAX_ANSWERS_BY_QUESTION, CorrectAnswers.size() + incorrectAnswers.size());
        	}
        }
    }

    @Test
    @Transactional
    public void testInsertExam() {
        final List<Exam> exams = examRepository.findAll();
        assertEquals(MAX_EXAMS, exams.size());
        final List<Question> questions = questionRepository.findByExamId(exams.get(0).getId());
        assertEquals(MAX_QUESTIONS_FOR_EXAM_1, questions.size());
        final ExamResult result = new ExamResult();
        result.setExam(exams.get(0));
        result.setStartTime(Calendar.getInstance().getTime());
        result.setQuestionCount(questions.size());
        examResultRepository.saveAndFlush(result);
    }

    @Test
    public void findsAllExamsQuestionsAnswers() {
        final List<Exam> exams = examRepository.findAll();
        assertEquals(MAX_EXAMS, exams.size());
        for(final Exam exam : exams) {
            final List<Question> questions = questionRepository.findByExam(exam);
            assertTrue(!questions.isEmpty());
            for(final Question question : questions) {
                final List<Answer> answers = answerRepository.findByQuestion(question);
                assertEquals(MAX_ANSWERS_BY_QUESTION, answers.size());
            }
        }
    }
}
