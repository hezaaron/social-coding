package org.onlinetest.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.onlinetest.entity.User;
import org.onlinetest.entity.UserProfile;
import org.onlinetest.service.TestExamService;
import org.onlinetest.service.UserProfileService;
import org.onlinetest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AdminController {

	@Autowired
	private UserService userService;
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
    
    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
     @RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
     public String saveUser( @Valid User user, BindingResult result, ModelMap model) {
	     if (result.hasErrors()) {
	    	 return "registration";
	     }
	     
	     // Check that userName is not unique and provide custom error with internationalised messages
	     if(!userService.isUserNameUnique(user.getId(), user.getUserName())){
		     FieldError userNameError = new FieldError("user", "userName",messageSource.getMessage( "non.unique.username", new String[]{user.getUserName()}, Locale.getDefault()));
		     result.addError(userNameError);
		     return "registration";
	     }
	     
	     userService.saveUser(user);
	     model.addAttribute( "success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
	     model.addAttribute( "loggedinuser", principalProvider.getPrincipal());
	     //return "success";
	     return "registrationsuccess" ;
     }
 
    /**
     * This method handles login GET requests.
     * If user is already logged-in and tries to go to login page again, will be redirected to 'testexamquestions' page.
     */
    @RequestMapping(value = "/description", method = RequestMethod.GET)
    public String homePage(ModelMap model) {
    	int examId = 1;
    	String examName = testExamService.getExamName(examId);
    	String examDescription = testExamService.getExamDescription(examId);
    	model.addAttribute("examName", examName);
    	model.addAttribute("examDescription", examDescription);
    	if (isCurrentAuthenticationAnonymous()) {
            return "description";
        } else {
            return "redirect:/testexamquestions";  
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
     * This method returns true if users are already authenticated (logged-in), else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
}
