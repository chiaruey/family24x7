package com.family.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Redirect Users to their home pages based on their roles.
 * 
 * @author Daddy
 */
@Controller
public class WelcomeController {
	
	@RequestMapping(value="/")
	public String welcomeHandler() {
		return "redirect:/home";		
	}
}
