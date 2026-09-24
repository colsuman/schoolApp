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

	    @GetMapping("/admissionsForm")
	    public String admissionsForm() {
	        return "admissionsForm";
	    }

	    @GetMapping("/feeStructure")
	    public String feeStructure() {
	        return "feeStructure";
	    }

	    @GetMapping("/documents")
	    public String documents() {
	        return "documents";
	    }

	    @GetMapping("/result")
	    public String result() {
	        return "result";
	    }

	    @GetMapping("/schoolAwards")
	    public String schoolAwards() {
	        return "schoolAwards";
	    }

	    @GetMapping("/gallery")
	    public String gallery() {
	        return "gallery";
	    }
}
