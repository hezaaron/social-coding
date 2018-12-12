package com.iplusplus.controller;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.iplusplus.dto.ExamDTO;
import com.iplusplus.entity.Answer;
import com.iplusplus.entity.Exam;
import com.iplusplus.entity.ExamResult;
import com.iplusplus.entity.Question;
import com.iplusplus.model.ExamTime;
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
    public Map<String, Object> getExam(@PathVariable("id") Integer examId, HttpServletRequest request) {
    	final Exam exam = examService.getExam(examId);
    	final List<Question> questions = examService.getQuestionsForExam(exam.getId());
    	final ExamResult examResult = new ExamResult();
    	examResult.setExam(exam);
    	examResult.setStartTime(new ExamTime(Clock.systemDefaultZone()).getTime());
    	examResult.setQuestionCount(questions.size());
    	request.getSession().setAttribute("examStarted", examResult.getStartTime());
        final int resultId = examService.createExamResult(examResult);
        final Map<String, Object> model = new HashMap<>();
        model.put("name", exam.getName());
        model.put("timer", getRemainingTime(request));
        model.put("result", new ExamDTO(resultId, examId));
        return model;
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
        examResult.setFinishTime(new ExamTime(Clock.systemDefaultZone()).getTime());
        examService.computeGrade(examResult, frm.getExamId(), frm.getAnswers());
        return ResponseEntity.ok(examService.updateExamResult(examResult));
    }

    @GetMapping("/result/{id}")
    ResponseEntity <Map<String,Object>> getExamStats(@PathVariable("id") Integer resultId) {
        return ResponseEntity.ok(examService.getExamStats(resultId));
    }

    private int getRemainingTime(HttpServletRequest request) {
    	LocalDateTime time = (LocalDateTime) (request.getSession().getAttribute("examStarted"));
    	final long start = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return  (int) ((examTimeMins * 60) - ((Calendar.getInstance().getTimeInMillis() - start) / 1000));
    }
    
}
