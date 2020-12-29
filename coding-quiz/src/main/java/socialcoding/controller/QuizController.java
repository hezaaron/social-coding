package socialcoding.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import socialcoding.entity.Answer;
import socialcoding.entity.Question;
import socialcoding.entity.Quiz;
import socialcoding.model.QuizSetter;
import socialcoding.service.QuizService;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public final class QuizController {
	
    private final QuizService quizService;
    
    @GetMapping
    public List<Quiz> getAllQuiz() {
    	return quizService.getAllQuiz();
    }
    
    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable("id") final Integer id) {
    	return quizService.getQuiz(id);
    }
    
    @GetMapping("/{id}/quiz-setter")
    public QuizSetter setQuiz(@PathVariable("id") final Integer quizId, final HttpServletRequest request) {
        return quizService.setQuiz(quizId, request);
    }

    @GetMapping("/{id}/questions")
    public List<Question> getQuestions(@PathVariable("id") final Integer quizId) {
        return quizService.getQuestions(quizId);
    }
    
    @GetMapping("/{id}/answers")
    public List<Answer> getAnswers(@PathVariable("id") final Integer quizId) {
        return quizService.getAnswers(quizId);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException exception) {
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}