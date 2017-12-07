package org.onlinetest.controller;

import org.onlinetest.service.TestExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@Transactional
@EnableWebMvc
public class MainController {

	@Autowired
	private TestExamService testExamService;
	
	
}
