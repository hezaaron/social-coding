package org.onlinetest.controller;

import org.onlinetest.service.TestExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private TestExamService testExamService;
	
	 /**
     * *This method provides test exam questions and their multiple choice questions one at a time
     */
    @RequestMapping(value = "/testexamquestions", method = RequestMethod.GET)
    public String examQuestion(ModelMap model) {
    	model.addAttribute("loggedinuser", getPrincipal());
        return "testexamquestions";
    }
    
    /**
     * This method returns the principal (user-name) of logged-in user.
     */
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
