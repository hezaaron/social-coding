package socialcoding.controller;

import java.time.Clock;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import socialcoding.dto.QuizDTO;
import socialcoding.entity.QuizAttempt;
import socialcoding.model.QuizReport;
import socialcoding.model.QuizTimer;
import socialcoding.service.ResultService;

@RestController
@RequestMapping("/results")
@RequiredArgsConstructor @Slf4j
public final class ResultController {

	private final ResultService resultService;
	
	@PostMapping
    public ResponseEntity<QuizAttempt> submitAnswer(@RequestBody final QuizDTO quizDto, final HttpServletRequest request) {
        log.info("Submit: {}", quizDto.getAnswers());
        final QuizAttempt quizAttempt = resultService.getQuizAttempt(quizDto.getId());
        quizAttempt.setFinishTime(new QuizTimer(Clock.systemDefaultZone()).getTime());
        resultService.computeGrade(quizAttempt, quizDto.getQuizId(), quizDto.getAnswers(), quizDto.getUsername());
        
        return ResponseEntity.ok(resultService.updateQuizAttempt(quizAttempt));
    }

    @GetMapping
    public ResponseEntity <QuizReport> getResult(@RequestParam("attemptId") final Long attemptId) {
        return ResponseEntity.ok(resultService.getResult(attemptId));
    }
    
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException exception) {
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
