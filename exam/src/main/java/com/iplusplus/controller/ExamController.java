package com.iplusplus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplusplus.dto.ExamResultDTO;
import com.iplusplus.entity.Answer;
import com.iplusplus.entity.Exam;
import com.iplusplus.entity.ExamResult;
import com.iplusplus.entity.Question;
import com.iplusplus.model.ExamTime;
import com.iplusplus.model.FactoryHelper;
import com.iplusplus.service.ExamService;

@RestController
@RequestMapping("/exams")
public class ExamController {
	
    private final ExamService examService;
    private final FactoryHelper factoryHelper;
    
    public ExamController(ExamService examService, FactoryHelper factoryHelper) {
    	this.examService = examService;
    	this.factoryHelper = factoryHelper;
    }
    
    @GetMapping
    public List<Exam> getAllExams() {
    	return examService.getAllExams();
    }
    
    @GetMapping("/exam/{id}")
    public Map<String, Object> getExam(@PathVariable("id") Integer examId, HttpServletRequest request) {
    	final Exam exam = examService.getExam(examId);
    	final ExamTime examTime = factoryHelper.makeExamTime();
    	ExamResult examResult = examService.createExamResult(exam);
    	request.getSession().setAttribute("examStarted", examResult.getStartTime());
        final int resultId = examService.getExamResultId(examResult);
        final ExamResultDTO examResultDto = factoryHelper.makeExamResultDto(resultId, examId);
        final Map<String, Object> model = new HashMap<>();
        model.put("name", exam.getName());
        model.put("timer", examTime.getRemainingTime(request));
        model.put("result", examResultDto);
        return model;
    }

    @GetMapping("/questions/{id}")
    public List<Question> getQuestionsForExam(@PathVariable("id") Integer examId) {
        return examService.getQuestionsForExam(examId);
    }
    
    @GetMapping("/exam-answers/{id}")
    public List<Answer> getAnswersForExam(@PathVariable("id") Integer examId) {
        return examService.getAnswersForExam(examId);
    }

    @GetMapping("/answers/{id}")
    public List<Answer> getAnswersForQuestion(@PathVariable("id") Integer questionId) {
        return examService.getAnswersForQuestion(questionId);
    }
}