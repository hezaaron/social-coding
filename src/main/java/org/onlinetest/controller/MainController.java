package org.onlinetest.controller;

import java.util.List;

import org.onlinetest.entity.Question;
import org.onlinetest.entity.QuestionChoice;
import org.onlinetest.service.TestExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController {

	
	@Autowired
	private PrincipalProvider principalProvider;
	@Autowired
	private TestExamService testExamService;
	
	 /**
     * *This method provides test exam questions and their multiple choice one at a time
     */
    @RequestMapping(value = "/testexamquestions", method = RequestMethod.GET)
    public String examQuestion(ModelMap model) {
    	int examId = 1;
    	model.addAttribute("loggedinuser", principalProvider.getPrincipal());
    	List<Question> examQuestions = testExamService.loadExam(examId);
    	List<QuestionChoice> questionChoices = testExamService.getQuestionChoices(examId);
    	model.addAttribute("examQuestions", examQuestions);
    	model.addAttribute("questionChoices", questionChoices);
        return "testexamquestions";
    }
    
}
