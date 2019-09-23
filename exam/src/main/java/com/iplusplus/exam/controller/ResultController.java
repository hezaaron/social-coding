package com.iplusplus.exam.controller;

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

import com.iplusplus.exam.dto.ExamDTO;
import com.iplusplus.exam.entity.ExamProtocol;
import com.iplusplus.exam.model.ExamTime;
import com.iplusplus.exam.service.ResultService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/results")
@Slf4j @RequiredArgsConstructor
public class ResultController {

	private final ResultService resultService;
	
	@PostMapping
    public ResponseEntity<ExamProtocol> submitResult(@RequestBody ExamDTO frm, HttpServletRequest request) {
        log.info("Submit: {}", frm.getAnswers());
        final ExamProtocol examProtocol = resultService.getExamProtocol(frm.getId());
        examProtocol.setFinishTime(new ExamTime(Clock.systemDefaultZone()).getTime());
        resultService.computeGrade(examProtocol, frm.getExamId(), frm.getAnswers(), frm.getUsername());
        return ResponseEntity.ok(resultService.updateExamProtocol(examProtocol));
    }

    @GetMapping("/{id}")
    public ResponseEntity <Map<String,Object>> getExamStats(@PathVariable("id") Long resultId) {
        return ResponseEntity.ok(resultService.getExamStats(resultId));
    }
    
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException exception) {
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
