package com.iplusplus.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplusplus.domain.Answer;
import com.iplusplus.domain.Exam;
import com.iplusplus.domain.ExamResult;
import com.iplusplus.domain.Question;
import com.iplusplus.dto.ExamDTO;
import com.iplusplus.service.ExamService;

@RestController
@RequestMapping("/testexams")
public class ExamController {
	
	private static final Logger logger = LogManager.getLogger(ExamController.class);
	@Value("${exam.time.minutes}")
    private Integer examTimeMins;
    @Autowired
    private ExamService examService;
    
    
    @GetMapping
    public List<Exam> getAllExams() {
    	return examService.getAllExams();
    }
    
    @GetMapping("/{id}")
    public List<Object> getExam(@PathVariable("id") Integer examId, HttpServletRequest request) {
    	
    	final Exam exam = examService.getExam(examId);
    	final List<Question> questions = examService.getQuestionsForExam(examId);
    	final ExamResult examResult = new ExamResult();
    	
    	examResult.setExam(exam);
    	examResult.setStartTime(Calendar.getInstance().getTime());
    	examResult.setQuestionCount(questions.size());
    	request.getSession().setAttribute("examStarted", examResult.getStartTime().getTime());
        final int resultId = examService.insertExam(examResult);
        
        return Arrays.asList(exam.getName(), getRemainingTime(request), new ExamDTO(resultId, examId));
    }

    @GetMapping("/questions/{id}")
    public List<Question> getQuestionsForExam(@PathVariable("id") Integer examId) {
        return examService.getQuestionsForExam(examId);
    }

    @GetMapping("/answers/{id}")
    public List<Answer> getAnswersForQuestion(@PathVariable("id") Integer questionId) {
        return examService.getAnswersForQuestion(questionId);
    }

    @PostMapping("/save")
    ResponseEntity<ExamResult> submitResult(@RequestBody ExamDTO frm, HttpServletRequest request) {
        logger.info("Submit: {}", frm.getAnswers());
        final ExamResult examResult = examService.getExamResult(frm.getId());
        examResult.setFinishTime(Calendar.getInstance().getTime());
        examService.calculateGrade(examResult, frm.getExamId(), frm.getAnswers());
        
        return ResponseEntity.ok(examService.updateExamResult(examResult));
    }

    @GetMapping("/result/{id}")
    ResponseEntity <List<Object>> getExamStats(@PathVariable("id") Integer resultId) {
        return ResponseEntity.ok(examService.getExamStats(resultId));
    }

    public Integer timer(HttpServletRequest request) {
        return getRemainingTime(request);
    }
    
    private int getRemainingTime(HttpServletRequest request) {
        final long start = (long) request.getSession().getAttribute("examStarted");
        return  (int) ((examTimeMins * 60) - ((Calendar.getInstance().getTimeInMillis() - start) / 1000));
    }
    
}
