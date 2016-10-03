package com.family.web.controller;

import java.util.Calendar;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.family.enums.RoleEnum;
import com.family.enums.SecureQuestionEnum;
import com.family.service.AccountService;
import com.family.service.bean.FamilyBean;
import com.family.service.bean.UserBean;
import com.family.web.controller.model.FamilyMemberModel;
import com.family.web.controller.model.MemberModel;
import com.family.web.dto.VerificationDto;
import com.family.web.util.MyCalendarUtils;
import com.family.web.util.UserSession;

@SessionAttributes("newMemberInfo")
@Controller
public class RegistrationController {

	public static final String REGISTRATION_STEP = "REGISTRATION_STEP";

	private static Logger logger = Logger
			.getLogger(RegistrationController.class.getName());

	enum RegStep {
		Step1, Step2, Step3
	}

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/registration/regStep1", method = RequestMethod.GET)
	public ModelAndView regStep1(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("reg.step1.view");
		request.getSession().setAttribute(REGISTRATION_STEP, RegStep.Step1);

		mav.addObject("allRoles", RoleEnum.values());
		mav.addObject("yearList", MyCalendarUtils.getYearList(100));
		mav.addObject("monthList", MyCalendarUtils.getMonthList());
		mav.addObject("dayList", MyCalendarUtils.getDayList());
		mav.addObject("secureQuestionList", SecureQuestionEnum.values());

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

	@RequestMapping(value = "/registration/regStep2", method = RequestMethod.GET)
	public ModelAndView regStep2(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("reg.step2.view");
		request.getSession().setAttribute(REGISTRATION_STEP, RegStep.Step2);

		mav.addObject("allRoles", RoleEnum.values());
		mav.addObject("yearList", MyCalendarUtils.getYearList(100));
		mav.addObject("monthList", MyCalendarUtils.getMonthList());
		mav.addObject("dayList", MyCalendarUtils.getDayList());
		FamilyMemberModel newFamilyMemberInfo = new FamilyMemberModel();
		mav.addObject("newFamilyMemberInfo", newFamilyMemberInfo);

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

	@RequestMapping(value = "/registration/getDateList.json", method = RequestMethod.GET)
	public @ResponseBody Set<Integer> getDateList(
			@RequestParam(value = "year", required = false) String yearStr,
			@RequestParam(value = "month", required = true) String monthStr) {

		Set<Integer> dayList = null;

		if (NumberUtils.isDigits(monthStr) && NumberUtils.isDigits(yearStr)) {
			dayList = MyCalendarUtils.getDayList(Integer.parseInt(yearStr),
					Integer.parseInt(monthStr));
		} else if (NumberUtils.isDigits(monthStr)
				&& NumberUtils.isDigits(yearStr) == false) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			dayList = MyCalendarUtils
					.getDayList(year, Integer.parseInt(monthStr));
		}

		return dayList;

	}
	
	@RequestMapping(value = "/registration/isExistingFamilyName.json", method = RequestMethod.GET)
	public @ResponseBody VerificationDto isExistingFamilyName(
			@RequestParam(value = "familyName", required = false) String familyName) {
		VerificationDto dto = new VerificationDto();

		FamilyBean family = null; 
		
		try {
			family = accountService.findFamilyByFamilyName(familyName);
			dto.setSuccess(true);
		} catch(Exception e) {
			logger.error("isExistingFamilyName> " + e.getMessage());
		}
		
		dto.setResult(family != null);
		return dto;
	}

	@RequestMapping(value = "/registration/isExistingUsername.json", method = RequestMethod.GET)
	public @ResponseBody VerificationDto isExistingUsername(
			@RequestParam(value = "username", required = false) String username) {
		VerificationDto dto = new VerificationDto();
		UserBean user = null; 
		
		try {
			user = accountService.findUserByUsername(username);
			dto.setSuccess(true);
		} catch(Exception e) {
			logger.error("isExistingUsername> " + e.getMessage());
		}
		
		dto.setResult(user != null);
		return dto;
	}

	/**
	 * Just a test, needs to be removed
	 * 
	 * @return
	 */
	@ModelAttribute("newMemberInfo")
	public MemberModel getNewModelInfo(ModelMap model) {
		MemberModel newMemberInfo = (MemberModel) model.get("newMemberInfo");

		if (newMemberInfo == null) {
			newMemberInfo = new MemberModel();
			model.put("newMemberInfo", newMemberInfo);
		} else {
			logger.info("getNewModelInfo> email=" + newMemberInfo.getEmail()
					+ ", firstName" + newMemberInfo.getFirstName()
					+ ",lastName = " + newMemberInfo.getLastName()
					+ ", password = " + newMemberInfo.getPassword()
					+ ",dobMonth = " + newMemberInfo.getDobMonth()
					+ ", dobDay = " + newMemberInfo.getDobDay() + ", dobYear"
					+ newMemberInfo.getDobYear() + ", gender = "
					+ newMemberInfo.getGender() + ", role = "
					+ newMemberInfo.getRole());
		}
		return newMemberInfo;

	}

	/**
	 * This is for adding HouseHead
	 */
	@RequestMapping(value = "/registration/addHouseHead", method = RequestMethod.POST)
	public ModelAndView addHouseHead(HttpServletRequest request,
			@ModelAttribute("newMemberInfo") MemberModel houseHeadInfo) {
		ModelAndView mav = new ModelAndView();

		try {
			
			UserBean user = accountService.findUserByUsername(houseHeadInfo.getUsername());

			if (user != null && !StringUtils.isEmpty(user.getUsername())) {
				mav.setViewName("redirect:/registration/regStep1");
				mav.addObject("success", false);
				mav.addObject("errorMsg",
						"This email " + houseHeadInfo.getEmail()
								+ " has already been registered in the system.");
				return mav;
			}

			houseHeadInfo.setAdmin(Boolean.TRUE.toString());
			accountService.addHouseHead(houseHeadInfo);
			mav.setViewName("redirect:/registration/regStep2");
			mav.addObject("success", true);
			UserSession.storeHouseheadInfo(request.getSession(), houseHeadInfo);
		} catch (Exception e) {
			mav.setViewName("redirect:/registration/regStep1");
			logger.error(
					"RegistrationController.addNewMember>" + e.getMessage(), e);
			mav.addObject("success", false);
			mav.addObject(
					"errorMsg",
					"Sorry, your registration has failed due to : "
							+ e.getMessage());

		}

		return mav;

	}

	/**
	 * This is for adding non-househead family member
	 */
	@RequestMapping(value = "/registration/addNewFamilyMember", method = RequestMethod.POST)
	public ModelAndView addNewFamilyMember(
			HttpServletRequest request,
			@ModelAttribute("newFamilyMemberInfo") FamilyMemberModel newFamilyMemberInfo) {
		ModelAndView mav = new ModelAndView();
/*
		UserBean user = accountService.findUserByUsername(newFamilyMemberInfo.getUsername());

		if (user != null && !StringUtils.isEmpty(user.getEmail())) {
			mav.setViewName("redirect:/registration/regStep2");
			mav.addObject("success", false);
			mav.addObject("errorMsg",
					"This email " + newFamilyMemberInfo.getEmail()
							+ " has already been registered in the system.");
			return mav;
		}
*/
		MemberModel househead = UserSession.retrieveHouseheadInfo(request
				.getSession());

		if (househead == null)
			throw new RuntimeException("House Head not found!");

		try {
			UserBean houseHead = accountService.findUserByUsername(househead.getUsername());
			accountService.addFamilyMember(houseHead, newFamilyMemberInfo);
			mav.setViewName("redirect:/registration/regStep2");
			mav.addObject("success", true);

			MemberModel newMemberInfo = (MemberModel) request.getSession()
					.getAttribute("newMemberInfo");

			if (newMemberInfo != null) {
				newMemberInfo.addFamilyMember(newFamilyMemberInfo);
			}

		} catch (Exception e) {
			mav.setViewName("redirect:/registration/regStep1");
			logger.error(
					"RegistrationController.addNewMember>" + e.getMessage(), e);
			mav.addObject("success", false);
			mav.addObject(
					"errorMsg",
					"Sorry, your registration has failed due to : "
							+ e.getMessage());

		}

		return mav;
	}
}
