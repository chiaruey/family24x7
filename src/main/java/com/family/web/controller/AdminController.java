package com.family.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.family.enums.GenderEnum;
import com.family.enums.IoeEnum;
import com.family.enums.RoleEnum;
import com.family.enums.SecureQuestionEnum;
import com.family.security.SecurityUtil;
import com.family.security.MyUserDetailsImpl;
import com.family.service.AccountService;
import com.family.service.AddressService;
import com.family.service.MoneyService;
import com.family.service.bean.FamilyBean;
import com.family.service.bean.MoneyTransactionTypeBean;
import com.family.service.bean.UserBean;
import com.family.util.MyUtils;
import com.family.web.dto.ActivateDto;
import com.family.web.dto.StandardDto;
import com.family.web.dto.UserDto;
import com.family.web.dto.VerificationDto;
import com.family.web.util.MyCalendarUtils;

@Controller
public class AdminController {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private AccountService accountService;

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private MoneyService moneyService;

	private static final String ADDRESS_UPDATED = "addressUpdated";
	private static final String ACCOUNT_UPDATED = "accountUpdated";

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView showManageMyAccount() {

		MyUserDetailsImpl ud = SecurityUtil.getUserDetails();

		ModelAndView mav = new ModelAndView("admin.view");
		mav.addObject("user", ud.getUser());
		mav.addObject("secureQuestionList", SecureQuestionEnum.values());
		
		FamilyBean family = accountService.findFamily(Long.valueOf(ud.getUser().getFamilyId()));
		mav.addObject("family", family);

		return mav;
	}


	@RequestMapping(value = "/admin/ManageMyFamily", method = RequestMethod.GET)
	public ModelAndView showAdmin() {

		MyUserDetailsImpl ud = SecurityUtil.getUserDetails();

		ModelAndView mav = new ModelAndView("manage.family.view");

		FamilyBean family = accountService.findFamily(Long.valueOf(ud.getUser().getFamilyId()));
		mav.addObject("family", family);
		mav.addObject("user", ud.getUser());
		mav.addObject("allRoles", RoleEnum.values());
		mav.addObject("yearList", MyCalendarUtils.getYearList(100));
		mav.addObject("monthList", MyCalendarUtils.getMonthList());
		mav.addObject("dayList", MyCalendarUtils.getDayList());

		return mav;
	}
	
	
	@RequestMapping(value = "/admin/manageAddress", method = RequestMethod.GET)
	public ModelAndView showManageAddress() {

		MyUserDetailsImpl ud = SecurityUtil.getUserDetails();

		ModelAndView mav = new ModelAndView("manage.address.view");
		FamilyBean family = accountService.findFamily(Long.valueOf(ud.getUser().getFamilyId()));
		mav.addObject("family", family);
		mav.addObject("user", ud.getUser());

		return mav;
	}
	

	@RequestMapping(value = "/admin/manageMonetaryTracType", method = RequestMethod.GET)
	public ModelAndView showManageMonetaryTracType() {

		MyUserDetailsImpl ud = SecurityUtil.getUserDetails();
		
		List<MoneyTransactionTypeBean> tracTypeList = moneyService.findFamilyTransactionTypes(ud.getUser().getFamilyId());
		
		List<MoneyTransactionTypeBean> incomeTypeList = new ArrayList<MoneyTransactionTypeBean>();
		
		List<MoneyTransactionTypeBean> expenseTypeList = new ArrayList<MoneyTransactionTypeBean>();

		if (CollectionUtils.isNotEmpty(tracTypeList)) {
			for (MoneyTransactionTypeBean tracType: tracTypeList) {
				if (tracType.getIoe().equals(IoeEnum.I.name())) {
					incomeTypeList.add(tracType);
				} else {
					expenseTypeList.add(tracType);
				}
			}
		}
		
		ModelAndView mav = new ModelAndView("manage.monetary.transition.type.view");
		FamilyBean family = accountService.findFamily(Long.valueOf(ud.getUser().getFamilyId()));
		mav.addObject("family", family);

		mav.addObject("user", ud.getUser());
		mav.addObject("incomeTypeList", incomeTypeList);
		mav.addObject("expenseTypeList", expenseTypeList);
		
		return mav;
	}
		
	@RequestMapping(value = "/admin/getDateList.json", method = RequestMethod.GET)
	public @ResponseBody
	Set<Integer> getDateList(
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

	/**
	 * This is for adding non-househead family member
	 */
	@RequestMapping(value = "admin/addNewFamilyMember", method = RequestMethod.POST)
	public @ResponseBody StandardDto addNewFamilyMember(HttpServletRequest request,
			String username, String email, String firstName, String lastName,
			String password, String gender, String dobYearStr,
			String dobMonthStr, String dobDayStr, String role, String adminStr) {
		StandardDto resp = new StandardDto();

		UserBean user = accountService.findUserByUsername(username);

		if (user != null && !StringUtils.isEmpty(user.getEmail())) {
			resp.setSuccess(false);
			resp.setErrorMsg("This email " + email
					+ " has already been registered in the system.");
			return resp;
		}

		try {
			// the logged in user
			UserBean loggedInUser = SecurityUtil.getUserDetails().getUser();
			
			accountService.addFamilyMember(loggedInUser, username, email, firstName,
					lastName, password, gender, dobYearStr, dobMonthStr,
					dobDayStr, role, Boolean.parseBoolean(adminStr));


			SecurityUtil.getUserDetails().setUser(loggedInUser);
			resp.setSuccess(true);

		} catch (Exception e) {
			logger.error(
					"addNewFamilyMember.addNewFamilyMember>" + e.getMessage(),
					e);
			resp.setSuccess(false);
			resp.setErrorMsg("Sorry, your registration has failed due to : "
					+ e.getMessage());
		}

		return resp;
	}

	/**
	 * This is for deleting non-househead family member
	 */
	@RequestMapping(value = "/admin/deleteFamilyMember", method = RequestMethod.POST)
	public @ResponseBody StandardDto deleteFamilyMember(HttpServletRequest request,
			String username) {
		
		StandardDto resp = new StandardDto();
		try {
			// the logged in user
			UserBean loggedInUser = SecurityUtil.getUserDetails().getUser();

			if (loggedInUser.getUsername().equalsIgnoreCase(username)) {
				resp.setSuccess(false);

				resp.setErrorMsg("You can not delete yourself from the system.");

				return resp;
			}

			accountService.deleteFamilyMember(username, loggedInUser);
			loggedInUser = accountService.findUserByUsername(loggedInUser.getUsername());
			SecurityUtil.getUserDetails().setUser(loggedInUser);
			resp.setSuccess(true);

		} catch (Exception e) {
			logger.error("deleteFamilyMember>" + e.getMessage(), e);
			resp.setSuccess(false);

			resp.setErrorMsg("Sorry, fail to delete family member due to : "
							+ e.getMessage());

		}

		return resp;
	}
	
	@RequestMapping(value = "admin/activateFamilyMember", method = RequestMethod.POST)
	public @ResponseBody ActivateDto activateFamilyMember(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "activate", required = true) String activate) {

		ActivateDto resp = new ActivateDto();
		try {
			logger.info("activateFamilyMember> username = " + username);
			
			// the logged in user
			UserBean loggedInUser = SecurityUtil.getUserDetails().getUser();

			if (loggedInUser.getUsername().equalsIgnoreCase(username)) {
				resp.setSuccess(false);

				resp.setErrorMsg("You can not deactive yourself from the system.");

				return resp;
			}

			accountService.activateFamilyMember(username, loggedInUser, Boolean.valueOf(activate));
			
			loggedInUser = accountService.findUserByUsername(loggedInUser.getUsername());
			SecurityUtil.getUserDetails().setUser(loggedInUser);
			resp.setSuccess(true);

		} catch (Exception e) {
			resp.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}


	/**
	 * This is for updaging non-househead family member
	 */					
	@RequestMapping(value = "admin/updateFamilyMember", method = RequestMethod.POST)
	public @ResponseBody StandardDto updateFamilyMember(String username, String email, String firstName, String lastName,
			String password, String gender, String dobYearStr,
			String dobMonthStr, String dobDayStr, String role, String adminStr) {
		StandardDto resp = new StandardDto();

		UserBean user = accountService.findUserByUsername(username);

		try {

			accountService.updateUser(user, email, firstName, lastName,
					password, GenderEnum.valueOf(gender), dobYearStr, dobMonthStr, dobDayStr, role,
					MyUtils.isTrue(adminStr));

			UserBean loggedInUser = SecurityUtil.getUserDetails().getUser();

			SecurityUtil.getUserDetails().setUser(loggedInUser);
			resp.setSuccess(true);
		} catch (Exception e) {
			logger.error("updateFamilyMember" + e.getMessage(), e);
			resp.setSuccess(false);
			resp.setErrorMsg("Sorry, fail to update family member due to : "
					+ e.getMessage());

		}

		return resp;
	}

	
	
	/**
	 * This is for reseting non-househead family member
	 */
	@RequestMapping(value = "admin/resetFamilyPassword", method = RequestMethod.POST)
	public @ResponseBody StandardDto resetFamilyPassword(String username, String password) {
		StandardDto resp = new StandardDto();

		UserBean user = accountService.findUserByUsername(username);

		try {
			boolean accountUpdated = false;
			accountUpdated = !password.equals(user.getPassword()) ;		
			
			if (accountUpdated) {
				accountService.resetFamilyPassword(SecurityUtil.getUserDetails().getUser(), user.getId(), username, password);

				UserBean loggedInUser = SecurityUtil.getUserDetails().getUser();

				SecurityUtil.getUserDetails().setUser(loggedInUser);			
			}

			resp.setSuccess(true);
		} catch (Exception e) {
			logger.error("updateFamilyMember" + e.getMessage(), e);
			resp.setSuccess(false);
			resp.setErrorMsg("Sorry, fail to reset family password due to : "
					+ e.getMessage());

		}

		return resp;
	}
	
	/**
	 * This is for updating accountSettings
	 * 	
	 */
	@RequestMapping(value = "admin/updateAccount", method = RequestMethod.POST)
	public ModelAndView updateAccount(HttpServletRequest request,
			String userId, String email, String firstName, String lastName,
			String secureQuestion, String secureQuestionAnswer) {
		ModelAndView mav = new ModelAndView("admin.view");
		try {
			UserBean user = accountService.findUserByUserid(Long.parseLong(userId));
			
			if (user != null) {
				boolean accountUpdated = false;
				accountUpdated = (!email.equalsIgnoreCase(user.getEmail())) || (!firstName.equalsIgnoreCase(user.getFirstName())) ||
						(!lastName.equalsIgnoreCase(user.getLastName()))|| 
					(!(Long.parseLong(secureQuestion) == user.getSecureQuestionId())) || (!secureQuestionAnswer.equals(user.getSecureQuestionAnswer()));

				if (accountUpdated) {
					accountService.updateUser(user, email, firstName, lastName,
							Long.parseLong(secureQuestion), secureQuestionAnswer);
					user = accountService.findUserByUserid(Long.parseLong(userId));
					SecurityUtil.getUserDetails().setUser(user);
				}
			}
			mav.addObject("user", SecurityUtil.getUserDetails().getUser());
			mav.addObject("secureQuestionList", SecureQuestionEnum.values());
			mav.addObject(ACCOUNT_UPDATED, true);
			

		} catch (Exception e) {
			logger.error("updateAccount" + e.getMessage(), e);
			mav.addObject("success", false);

		}

		return mav;
	}

	
	
	@RequestMapping(value = "/admin/showEditUser", method = RequestMethod.GET)
	public @ResponseBody UserDto showEditUser(String username) {
		 UserDto userDto = new UserDto();

		try {
			UserBean familyMember = accountService.findUserByUsername(username);
			
			userDto = new UserDto(familyMember);
			userDto.setHoh(accountService.isHouseHead(familyMember.getFamilyId(), familyMember.getId()));
			userDto.setSuccess(true);
		} catch (Exception e) {
			userDto.setSuccess(false);
			userDto.setErrorMsg(e.getMessage());
		}

		return userDto;
	}

	// //////////////////////////////////
	// Address Management
	// //////////////////////////////////

	@RequestMapping(value = "/admin/updateAddress", method = RequestMethod.POST)
	public ModelAndView updateAddress(HttpServletRequest request,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "street", required = true) String street,
			@RequestParam(value = "city", required = true) String city,
			@RequestParam(value = "state", required = true) String state,
			@RequestParam(value = "country", required = true) String country,
			@RequestParam(value = "zip", required = true) String zip) {

		ModelAndView mav = new ModelAndView("manage.address.view");

		mav.addObject("user", SecurityUtil.getUserDetails().getUser());


		try {
			addressService.updateAddress(Long.parseLong(id), street, city,
					state, country, zip);

			UserBean loggedInUser = SecurityUtil.getUserDetails().getUser();
			loggedInUser = accountService.findUserByUsername(loggedInUser.getUsername());
			SecurityUtil.getUserDetails().setUser(loggedInUser);
			FamilyBean family = accountService.findFamily(Long.valueOf(SecurityUtil.getUserDetails().getUser().getFamilyId()));
			mav.addObject("family", family);
			mav.addObject("success", true);

			mav.addObject("user", loggedInUser);

			mav.addObject(ADDRESS_UPDATED, true);
		} catch (Exception e) {
			logger.error("Fail to update address id = " + id, e);
			mav.addObject("success", false);
		}

		return mav;
	}
	
	@RequestMapping(value = "/admin/isAdult.json", method = RequestMethod.GET)
	public @ResponseBody VerificationDto isAdult(
			@RequestParam(value = "year", required = true) String yearStr,
			@RequestParam(value = "month", required = true) String monthStr,
			@RequestParam(value = "day", required = true) String dayStr,
			@RequestParam(value = "adultAge", required = true) String adultAgeStr) {
		VerificationDto dto = new VerificationDto();

		
		try {
			Date dob = new GregorianCalendar(Integer.parseInt(yearStr), Integer.parseInt(monthStr), Integer.parseInt(dayStr)).getTime();
			int age = MyCalendarUtils.getAge(dob);
			int adultAge = Integer.parseInt(adultAgeStr);
			dto.setResult(age >= adultAge);
			dto.setSuccess(true);
			logger.error("age =" + age + ", adultAgeStr = " + adultAgeStr + ", dto.getResult() = " + dto.isResult());
		} catch(Exception e) {
			logger.error("isExistingFamilyName> " + e.getMessage());
			dto.setResult(false);
			dto.setSuccess(false);
		}
		
		return dto;
	}


}
