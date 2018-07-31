package com.iplusplus.controller;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iplusplus.domain.Exam;
import com.iplusplus.domain.ExamResult;
import com.iplusplus.domain.Question;
import com.iplusplus.dto.EntryDTO;
import com.iplusplus.dto.ExamDTO;
import com.iplusplus.service.ExamService;
import com.iplusplus.util.Converter;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ExamController {
	
	private static final Logger logger = LogManager.getLogger(ExamController.class);

	@Value("${exam.time.minutes}")
    private Integer examTimeMins;

    @Autowired
    private ExamService examService;

    @GetMapping("/testexams")
    public Collection<Exam> getTestExams() {
    	return examService.getTestExams();
    }
    
    @GetMapping("/testexam/{id}")
    public Map<String, Object> getExam(@PathVariable("id") Integer examId, HttpServletRequest request) {
    	
    	final Exam exam = examService.getExam(examId);
    	final List<Question> questions = examService.getQuestionsForExam(examId);
    	final ExamResult examResult = new ExamResult();
    	examResult.setExam(exam);
    	examResult.setStart(Calendar.getInstance().getTime());
    	examResult.setQuestionCount(questions.size());

        final int resultId = examService.insertExam(examResult);
        
        Map<String, Object> map = new HashMap<>();

        map.put("results", new ExamDTO(resultId, examId));
        map.put("examName", String.format("Welcome to \"%s\"!", exam.getName()));
        map.put("questions", Converter.questionsToDTO(questions));

        request.getSession().setAttribute("examId", examId);
        request.getSession().setAttribute("examStarted", examResult.getStart().getTime());
        final int remaining = getRemainingTime(request);
        
        map.put("examTime", remaining);

        return map;
    }

    private int getRemainingTime(HttpServletRequest request) {
        final long start = (long) request.getSession().getAttribute("examStarted");
        final int remaining = (int) ((examTimeMins * 60) - ((Calendar.getInstance().getTimeInMillis() - start) / 1000));
        return remaining;
    }

    @GetMapping("/questions/{id}")
    public List<Question> getQuestionsForExam(@PathVariable("id") Integer examId) {
        return examService.getQuestionsForExam(examId);
    }

    @GetMapping("/answers/{id}")
    public List<EntryDTO> getAnswersForQuestion(@PathVariable("id") Integer questionId) {
        return Converter.answersToDTO(examService.getAnswersForQuestion(questionId));
    }

    @PostMapping("/results")
    @ResponseBody
    public ExamResult submitResult( ExamDTO frm, HttpServletRequest request) {
        logger.info("Submit: {}", frm);
        request.getSession().removeAttribute("examId");

        final ExamResult examResult = examService.getExamResult(frm.getId());
        examResult.setFinish(Calendar.getInstance().getTime());

        examService.calcGrade(examResult, frm.getExamId(), frm.getAnswers());
        logger.info("Submit: {}", examResult);
        return examService.updateExamResult(examResult);
    }

    @GetMapping("/stat/{id}")
    public Map<String, Object> stat(@PathVariable("id") Integer resultId) {
        return examService.stats(resultId);
    }

    @GetMapping("/time")
    public Integer timer(HttpServletRequest request) {
        return getRemainingTime(request);
    }
    
}
