package org.onlinetest.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.onlinetest.dto.TestExamDTO;
import org.onlinetest.entity.ExamPractice;
import org.onlinetest.entity.Question;
import org.onlinetest.entity.QuestionChoice;
import org.onlinetest.service.ExamPracticeService;
import org.onlinetest.service.TestExamService;
import org.onlinetest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class ExamController {
	
	private static final Logger logger = LogManager.getLogger(ExamController.class);
	
	@Autowired
	private PrincipalProvider principalProvider;
	@Autowired
	private UserService userService;
	@Autowired
	private TestExamService testExamService;
	@Autowired
	private ExamPracticeService examPracticeService;
	
	 
    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public String exam(ModelMap model, HttpSession session) {
    	
    	int examId = (int)session.getAttribute("examId");
    	String loggedInUser = principalProvider.getPrincipal();
    	int user = userService.findByUserName(loggedInUser).getId();
    	List<Question> examQuestions = testExamService.getQuestions(examId);
    	ExamPractice examPractice = new ExamPractice();
    	
    	examPractice.setUser(user);
    	examPractice.setExam(examId);
    	examPractice.setStart(Calendar.getInstance().getTime());
    	examPractice.setQuestionCount(examQuestions.size());
    	
    	int examPracticeId = examPracticeService.saveAndGetId(examPractice);
    	    	
    	session.setAttribute("examPracticeId", examPracticeId);
    	session.setAttribute("examstarted", examPractice.getStart());
    	
    	int timeRemaining = getRemainingTime(session);
    	
    	model.addAttribute("examtime", timeRemaining);
    	model.addAttribute("loggedinuser", loggedInUser);
    	model.addAttribute("examid", examId);
    	
    	return "question";
    }
    
    @RequestMapping(value = "/questions/{id}")
    @ResponseBody
    public List<Question> getExamQuestions(@PathVariable ("id") Integer examId) {
    	return testExamService.getQuestions(examId);
    }
    
    @RequestMapping(value = "/choices/{id}")
    @ResponseBody
    public List<QuestionChoice> getQuestionChoices(@PathVariable ("id") Integer questionId) {
    	return testExamService.getQuestionChoices(questionId);
    }
    
    @RequestMapping(value = "/finish", method = RequestMethod.GET)
    public String endExam(ModelMap model, HttpSession session) {
    	String loggedInUser = principalProvider.getPrincipal();
    	int examId = (int)session.getAttribute("examId");
    	int examPracticeId = (int)session.getAttribute("examPracticeId");
    	
    	model.addAttribute("loggedinuser", loggedInUser);
    	model.addAttribute("results", new TestExamDTO(examPracticeId, examId));
    	
    	return "endexam";
    }
    
    @RequestMapping(value = "/endexam", method = RequestMethod.POST)
    public String submitExam(ModelMap model, @Valid @ModelAttribute("results") TestExamDTO form, BindingResult validation, HttpSession session) throws IOException {
    	if(validation.hasErrors()) {
    		return "endexam";
    	}
    	
    	logger.info("Submit : {}", form.getClass());
    	session.removeAttribute("examId");
    	session.removeAttribute("examPracticeId");
    	
    	ExamPractice examPractice = examPracticeService.getExamPractice(form.getId());
    	examPractice.setFinish(Calendar.getInstance().getTime());
    	examPracticeService.calculateGrade(examPractice, form.getExamId(), form.getAnswers());
    	examPracticeService.update(examPractice);
    	
    	logger.info("Submit : {}", examPractice);
    	
    	return "redirect:/logout";
    }
    
    private int getRemainingTime(HttpSession session) {
    	int examTimeMins = 20;
    	Date date = (Date) session.getAttribute("examstarted");
    	long start = date.getTime();
    	int remainingTime = (int) ((examTimeMins * 60) - ((Calendar.getInstance().getTimeInMillis() - start) / 1000));
    	return remainingTime;
    }
}
