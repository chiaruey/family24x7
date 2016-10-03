package com.family.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.family.service.AccountService;
import com.family.service.bean.UserBean;
import com.family.util.MyUtils;
import com.family.web.dto.StandardDto;
import com.family.web.dto.UsernameValdationDto;

/**
 * AccessHelp controller
 * 
 * @author clu3
 */
@Controller
public class AccessHelpController {

	public static final String ACCESS_HELP_STEP = "ACCESS_HELP_STEP";
	
	private Logger  logger = Logger.getLogger("com.family.web.controller");
	
	@Autowired
	private AccountService accountService;
	
	enum AccessHelpStepEnum {
		Step1, Step2, Step3
	}
	
	/////////////
	// Step 1
	
	@RequestMapping(value="/accessHelp/step1", method=RequestMethod.GET)	
	public ModelAndView showAccessHelpStep1(HttpServletRequest request) {	
		
		ModelAndView mav = new ModelAndView("access.help.step1.view");
		request.getSession().setAttribute(ACCESS_HELP_STEP, AccessHelpStepEnum.Step1);

		if (!StringUtils.isEmpty(request.getParameter("success"))) {
			String success = request.getParameter("success");
			if ("true".equalsIgnoreCase(success)
					|| "false".equalsIgnoreCase(success)) {
				mav.addObject("success", new Boolean(success));
			}
		}
		if (!StringUtils.isEmpty(request.getParameter("errorMsg"))) {
			String errorMsg = request.getParameter("errorMsg");
			mav.addObject("errorMsg", errorMsg);
		}

		return mav;
	
	}
	
	@RequestMapping(value = "/accessHelp/verifyUsername.json", method = RequestMethod.GET)
	public @ResponseBody UsernameValdationDto verifyUsername(
			@RequestParam(value = "username", required = true) String username) {
		
		UsernameValdationDto usernameValidationDto = new UsernameValdationDto();
		UserBean userBean = null; 
		
		try {
			userBean = accountService.findUserByUsername(username);
			if (userBean != null) {
				usernameValidationDto = new UsernameValdationDto(userBean);
			}
		} catch(Exception e) {
			logger.error("verifyUsername> " + e.getMessage());
		}
		
		return usernameValidationDto;
	}

	/////////////
	// Step 2


	@RequestMapping(value="/accessHelp/step2", method=RequestMethod.GET)	
	public ModelAndView showAccessHelpStep2(HttpServletRequest request,
			@RequestParam(value = "username", required = true) String username) {	
		
		AccessHelpStepEnum currentStep = (AccessHelpStepEnum) request.getSession().getAttribute(ACCESS_HELP_STEP);
		logger.error("showAccessHelpStep2: currentStep = " + currentStep);

		ModelAndView mav = new ModelAndView();
		if (currentStep == AccessHelpStepEnum.Step1 || currentStep == AccessHelpStepEnum.Step2 || currentStep == AccessHelpStepEnum.Step3) {
			mav.setViewName("access.help.step2.view");
			request.getSession().setAttribute(ACCESS_HELP_STEP, AccessHelpStepEnum.Step2);
			UserBean userBean = accountService.findUserByUsername(StringUtils.trim(username));
			
			if (userBean != null) {
				mav.addObject("username", username);
				mav.addObject("noSecureQuestion", userBean.getSecureQuestionId() == 0L);
				mav.addObject("secureQuestion", MyUtils.findSecureQuestionEnumById(userBean.getSecureQuestionId()));
				mav.addObject("secureQuestionAnswer", userBean.getSecureQuestionAnswer());		
				if (userBean.getSecureQuestionId() == 0L) {
					mav.addObject("errorMsg", "This account does not set tup security question. Please contact head of household to reset password");
				}
			}
			return mav;			
		} else {
			mav.setViewName("access.help.step1.view");
			mav.addObject("errorMsg", "Need to access help from step 1");
			return mav;
		}
			

	
	}
	
	@RequestMapping(value = "/accessHelp/verifySecurityQuestion.json", method = RequestMethod.GET)
	public @ResponseBody UsernameValdationDto verifySecurityQuestion(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "secureQuestionAnswer", required = true) String secureQuestionAnswer) {

		UsernameValdationDto usernameValidationDto = new UsernameValdationDto();
		UserBean userBean = null; 		
			
		try {
			userBean = accountService.findUserByUsername(username);
			if (userBean != null && secureQuestionAnswer.equalsIgnoreCase(userBean.getSecureQuestionAnswer())) {
				usernameValidationDto = new UsernameValdationDto(userBean);
			}
		} catch(Exception e) {
			logger.error("verifyUsername> " + e.getMessage());
		}
		
		return usernameValidationDto;
	}
	

	/////////////
	// Step 3

	@RequestMapping(value="/accessHelp/step3", method=RequestMethod.GET)	
	public ModelAndView showAccessHelpStep3(HttpServletRequest request,
			@RequestParam(value = "username", required = true) String username) {	
		
		AccessHelpStepEnum currentStep = (AccessHelpStepEnum) request.getSession().getAttribute(ACCESS_HELP_STEP);
		logger.error("showAccessHelpStep3: currentStep = " + currentStep);

		ModelAndView mav = new ModelAndView();
		if (currentStep == AccessHelpStepEnum.Step2 || currentStep == AccessHelpStepEnum.Step3) {
			mav.addObject("username", username);
			mav.setViewName("access.help.step3.view");
			request.getSession().setAttribute(ACCESS_HELP_STEP, AccessHelpStepEnum.Step3);		
		} else {
			UserBean userBean = accountService.findUserByUsername(username);
			
			if (userBean != null) {
				mav.setViewName("access.help.step2.view");
				mav.addObject("username", username);
				mav.addObject("noSecureQuestion", userBean.getSecureQuestionId() == 0L);
				mav.addObject("secureQuestion", MyUtils.findSecureQuestionEnumById(userBean.getSecureQuestionId()));
				mav.addObject("secureQuestionAnswer", userBean.getSecureQuestionAnswer());		
				if (userBean.getSecureQuestionId() == 0L) {
					mav.addObject("errorMsg", "This account does not set tup security question. Please contact head of household to reset password");
				}
			} else {
				mav.setViewName("access.help.step1.view");
				mav.addObject("errorMsg", "access validation error, please start over");		
			}
		}
		
		return mav;
	}
	
	@RequestMapping(value="accessHelp/resetPassword.json", method=RequestMethod.POST)
	public @ResponseBody StandardDto resetPassword(HttpServletRequest request,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {
		StandardDto resp = new StandardDto();

		UserBean user = accountService.findUserByUsername(username);

		try {

			if (user != null) {
				accountService.updateUser(user, user.getEmail(), user.getFirstName(), user.getLastName(),
						password, user.getSecureQuestionId(), user.getSecureQuestionAnswer());
				
			}
			resp.setSuccess(true);
		} catch (Exception e) {
			logger.error("resetPassword" + e.getMessage(), e);
			resp.setSuccess(false);
			resp.setErrorMsg("Sorry, the password reset has been failed. Please contact help desk to reset password ");

		}

		return resp;		
	}
	
}
