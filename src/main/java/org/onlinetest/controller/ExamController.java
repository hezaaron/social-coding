package org.onlinetest.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.onlinetest.entity.Question;
import org.onlinetest.entity.QuestionChoice;
import org.onlinetest.service.TestExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class ExamController {
	
	@Autowired
	private PrincipalProvider principalProvider;
	@Autowired
	private TestExamService testExamService;
	
	 
    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public String exam(ModelMap model, HttpSession session) {
    	
    	int examId = (int)session.getAttribute("examId");
    	String loggedInUser = principalProvider.getPrincipal();
    	model.addAttribute("loggedinuser", loggedInUser);
    	model.addAttribute("examId", examId);
    	
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
}
