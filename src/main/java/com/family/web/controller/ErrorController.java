package com.family.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Redirect Users to their error pages 
 * 
 * @author Daddy
 */
@Controller
public class ErrorController {
	
	@RequestMapping(value="/error/unsupportedBrowser")
	public ModelAndView unsupportedBrowser() {
		
		ModelAndView mav = new ModelAndView("error-unsupported-browser.view");
		
		return mav;
	}
}
