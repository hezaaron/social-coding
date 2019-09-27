package com.iplusplus.coding.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplusplus.coding.entity.Answer;
import com.iplusplus.coding.entity.Quiz;
import com.iplusplus.coding.entity.Question;
import com.iplusplus.coding.service.QuizService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuizController {
	
    private final QuizService quizService;
    
    @GetMapping
    public List<Quiz> getAllQuizzes() {
    	return quizService.getAllQuizzes();
    }
    
    @GetMapping("/details/{id}")
    public Map<String, Object> getQuizDetails(@PathVariable("id") Integer quizId, HttpServletRequest request) {
        return quizService.getQuizDetails(quizId, request);
    }

    @GetMapping("/questions/{id}")
    public List<Question> getQuestionsForQuiz(@PathVariable("id") Integer quizId) {
        return quizService.getQuestionsForQuiz(quizId);
    }
    
    @GetMapping("/answers/{id}")
    public List<Answer> getAnswersForQuiz(@PathVariable("id") Integer quizId) {
        return quizService.getAnswersForQuiz(quizId);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException exception) {
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}