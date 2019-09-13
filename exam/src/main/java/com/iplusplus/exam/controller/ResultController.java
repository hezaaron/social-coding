package com.iplusplus.exam.controller;

import java.time.Clock;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplusplus.exam.dto.ExamResultDTO;
import com.iplusplus.exam.entity.ExamResult;
import com.iplusplus.exam.model.ExamTime;
import com.iplusplus.exam.service.ExamService;

@RestController
@RequestMapping("/results")
public class ResultController {

	private static final Logger logger = LogManager.getLogger(ExamController.class);
	private final ExamService examService;
	
	public ResultController(ExamService examService) {
		this.examService = examService;
	}
	
	@PostMapping
    ResponseEntity<ExamResult> submitResult(@RequestBody ExamResultDTO frm, HttpServletRequest request) {
        logger.info("Submit: {}", frm.getAnswers());
        final ExamResult examResult = examService.getExamResult(frm.getId());
        examResult.setFinishTime(new ExamTime(Clock.systemDefaultZone()).getTime());
        examService.computeGrade(examResult, frm.getExamId(), frm.getAnswers(), frm.getUserName());
        return ResponseEntity.ok(examService.updateExamResult(examResult));
    }

    @GetMapping("/{id}")
    ResponseEntity <Map<String,Object>> getExamStats(@PathVariable("id") Integer resultId) {
        return ResponseEntity.ok(examService.getExamStats(resultId));
    }
}
