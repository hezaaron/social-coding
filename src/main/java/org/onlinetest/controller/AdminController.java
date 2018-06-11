package org.onlinetest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.onlinetest.entity.UserProfile;
import org.onlinetest.service.TestExamService;
import org.onlinetest.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AdminController {

	@Autowired
    private UserProfileService userProfileService;
	@Autowired
	private PrincipalProvider principalProvider;
	@Autowired
	MessageSource messageSource;
    @Autowired
    private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;
    @Autowired
    private TestExamService testExamService;
     
    
    /**
     * This method will provide UserProfile list to views
     */
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }
    
    @RequestMapping(value={"/", "/index"}, method=RequestMethod.GET)
    public String homePage() {
    	return "index";
    }
    /**
     * This method handles login GET requests.
     * If user is already logged-in and tries to go to login page again, will be redirected to 'question' page.
     */
    @RequestMapping(value = "/description", method = RequestMethod.GET)
    public String login(ModelMap model, HttpSession session) {
    	int examId = 1;
    	session.setAttribute("examId", examId);
    	String examName = testExamService.getExamName(examId);
    	String examDescription = testExamService.getExamDescription(examId);
    	
    	model.addAttribute("examName", examName);
    	model.addAttribute("examDescription", examDescription);
    	
    	if (isCurrentAuthenticationAnonymous()) {
            return "description";
        } else {
            return "redirect:/question";  
        }
    }
    
    /**
     * This method handles Access-Denied redirect.
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", principalProvider.getPrincipal());
        return "accessDenied";
    }
 
    /**
     * This method handles logout requests.
     */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){    
            //new SecurityContextLogoutHandler().logout(request, response, authentication);
            persistentTokenBasedRememberMeServices.logout(request, response, authentication);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/description?logout";
    }
     
    /**
     * This method returns true if user is not already authenticated (logged-in), else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
}
