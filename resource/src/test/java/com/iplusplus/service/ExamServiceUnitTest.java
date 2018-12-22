package com.iplusplus.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.iplusplus.entity.Exam;
import com.iplusplus.entity.ExamResult;
import com.iplusplus.entity.Question;
import com.iplusplus.model.ExamTime;
import com.iplusplus.repository.ExamRepository;
import com.iplusplus.repository.ExamResultRepository;
import com.iplusplus.repository.QuestionRepository;

class ExamServiceUnitTest {

	@InjectMocks
	ExamService examService;
	@Mock
	ExamRepository examRepository;
	@Mock
	QuestionRepository questionRepository;
	@Mock
	ExamResultRepository resultRepository;
	private Exam beginnerJava;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		beginnerJava = new Exam();
		beginnerJava.setId(1);
		beginnerJava.setName("Java Beginner Exam");
	}
	
	@Test
    void testFindsAllExams() {
		Exam intermediateJava = new Exam();
		intermediateJava.setId(2);
		intermediateJava.setName("Java Intermediate Exam");
		Exam advanceJava = new Exam();
		advanceJava.setId(3);
		advanceJava.setName("Java Advance Exam");
		List<Exam> javaExams = Arrays.asList(beginnerJava, intermediateJava, advanceJava);
		when(examRepository.findAll()).thenReturn(javaExams);
        final List<Exam> exams = examService.getAllExams();
        assertAll("exams",
        		() -> assertFalse(exams.isEmpty()),
        		() -> assertEquals(3, exams.size()),
        		() -> assertEquals("Java Intermediate Exam", exams.get(1).getName()));
    }

	@Test
	void testGetExam() {
		when(examRepository.getOne(anyInt())).thenReturn(beginnerJava);
		int examId = beginnerJava.getId();
		final Exam exam = examService.getExam(examId);
		assertAll("exam",
				() -> assertNotNull(exam),
				() -> assertEquals("Java Beginner Exam", exam.getName()));
	}
	
	@Test
	void testGetQuestionsForExam() {
		Question question1 = new Question();
		question1.setId(1);
		question1.setExam(beginnerJava);
		question1.setName("What is inheritance");
		Question question2 = new Question();
		question2.setId(2);
		question2.setExam(beginnerJava);
		question2.setName("What is composition");
		List<Question> examQuestions = Arrays.asList(question1, question2);
		when(questionRepository.findByExamId(anyInt())).thenReturn(examQuestions);
		int examId = beginnerJava.getId();
		final List<Question> questions = examService.getQuestionsForExam(examId);
		assertAll("question",
				() -> assertFalse(questions.isEmpty()),
				() -> assertTrue(questions.contains(question1)),
				() -> assertEquals(5, questions.size()));
	}
	
	@Test
	void testCreateExamResult() {
		ExamResult examResult = new ExamResult();
		examResult.setId(1);
		examResult.setExam(beginnerJava);
		when(resultRepository.saveAndFlush(any())).thenReturn(examResult);
		Integer savedExamResult = examService.createExamResult(examResult);
		assertEquals(examResult.getId(), savedExamResult);
	}
	
	@Test
	void testGetExamStats() {
		ExamResult result = new ExamResult();
		ExamTime time = new ExamTime(Clock.fixed(Instant.parse("2018-12-06T10:30:30.00Z"), ZoneId.systemDefault()));
		result.setId(1);
		result.setExam(beginnerJava);
		result.setStartTime(time.getTime());
		result.setFinishTime(time.getTime().plusMinutes(5));
		result.setQuestionCount(5);
		result.setGrade(80);
		when(resultRepository.getOne(anyInt())).thenReturn(result);
		Map<String, Object> stats = examService.getExamStats(result.getId());
		assertAll("stats",
				() -> assertFalse(stats.isEmpty()),
				() -> assertEquals("Your result for Java Beginner Exam", stats.get("title")),
				() -> assertEquals("11:35:30", stats.get("finishTime")),
				() -> assertEquals(80, stats.get("grade")));
	}
}