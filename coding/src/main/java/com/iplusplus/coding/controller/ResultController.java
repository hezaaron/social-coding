package com.iplusplus.coding.controller;

import java.time.Clock;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplusplus.coding.dto.QuizDTO;
import com.iplusplus.coding.entity.Protocol;
import com.iplusplus.coding.model.QuizTime;
import com.iplusplus.coding.service.ResultService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/results")
@Slf4j @RequiredArgsConstructor
public class ResultController {

	private final ResultService resultService;
	
	@PostMapping
    public ResponseEntity<Protocol> submitResult(@RequestBody QuizDTO frm, HttpServletRequest request) {
        log.info("Submit: {}", frm.getAnswers());
        final Protocol protocol = resultService.getProtocol(frm.getId());
        protocol.setFinishTime(new QuizTime(Clock.systemDefaultZone()).getTime());
        resultService.computeGrade(protocol, frm.getQuizId(), frm.getAnswers(), frm.getUsername());
        return ResponseEntity.ok(resultService.updateProtocol(protocol));
    }

    @GetMapping("/{id}")
    public ResponseEntity <Map<String,Object>> getQuizStats(@PathVariable("id") Long resultId) {
        return ResponseEntity.ok(resultService.getQuizStats(resultId));
    }
    
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException exception) {
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
