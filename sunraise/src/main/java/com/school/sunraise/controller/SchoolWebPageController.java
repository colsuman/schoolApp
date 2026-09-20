package com.school.sunraise.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.school.sunraise.model.StudentSignup;

@Controller
public class SchoolWebPageController {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(SchoolWebPageController.class);

	  @RequestMapping("/schoolPage")
	    public String showSchoolPage() {
	        return "schoolPage";
	    }

	    @GetMapping("/about")
	    public String about() {
	        return "about";
	    }

	    @GetMapping("/home")
	    public String home() {
	        return "schoolPage";
	    }
	    
	    @GetMapping("/director")
	    public String director() {
	        return "director";
	    }
	    
	    @GetMapping("/teacher")
	    public String teacher() {
	        return "teacher";
	    }
	    
	    @GetMapping("/login")
	    public String loginPage() {
	        return "login";
	    }
	    
	    @GetMapping("/signup")
	    public String signupPage(Model model) {
	        model.addAttribute("studentSignup", new StudentSignup());
	        return "signup";
	    }
	    
	    @GetMapping("/forgotPwd")
	    public String forgotPwd() {
	        return "forgotPwd";
	    }
}
