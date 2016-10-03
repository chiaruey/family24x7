package com.family.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.family.enums.IoeEnum;
import com.family.security.SecurityUtil;
import com.family.security.MyUserDetailsImpl;
import com.family.service.AccountService;
import com.family.service.MoneyService;
import com.family.service.bean.FamilyBean;
import com.family.service.bean.MoneyTransactionBean;
import com.family.service.bean.MoneyTransactionTypeBean;
import com.family.web.dto.ActivateDto;
import com.family.web.dto.MonthlyMoneyDto;
import com.family.web.dto.StandardDto;
import com.family.web.dto.MoneyTransactionDto;

@Controller
public class MoneyController {

	@Autowired
	private MoneyService moneyService;
	
	@Autowired
	private AccountService accountService;

	private Logger  logger = Logger.getLogger("com.family.web.controller");

	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	

	/**
	 * Add a new Money Transition
	 */
	@RequestMapping(value = "addMoneyTransition", method = RequestMethod.POST)
	public @ResponseBody StandardDto addMoneyTransition(
			@RequestParam(value = "transType", required = true) String transType,
			@RequestParam(value = "comments", required = true) String comments,
			@RequestParam(value = "amount", required = true) String amountStr,
			@RequestParam(value = "tracDate", required = true) String tranDateStr,
			@RequestParam(value = "newTransName", required = false) String newTransName) {

		StandardDto response = new StandardDto(false);
		try {
			Date tranDate = sdf.parse(tranDateStr);
			MyUserDetailsImpl ud = SecurityUtil.getUserDetails();
			
			FamilyBean family = accountService.findFamily(ud.getUser().getFamilyId());

			moneyService.addMoneyTransaction(family, tranDate,
					transType, comments, new BigDecimal(amountStr),
					SecurityUtil.getUserDetails().getUser());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;

	}

	/**
	 * Update an existing money transition
	 */
	@RequestMapping(value = "updMoneyTransition", method = RequestMethod.POST)
	public @ResponseBody StandardDto updMoneyTransition(
			@RequestParam(value = "transId", required = true) String tranTypeIdStr,
			@RequestParam(value = "tracDate", required = true) String tranDateStr,
			@RequestParam(value = "comments", required = true) String comments,
			@RequestParam(value = "amount", required = true) String amountStr) {
		StandardDto response = new StandardDto(false);
		try {
			long transId = NumberUtils.parseNumber(tranTypeIdStr, Long.class);
			Date tranDate = sdf.parse(tranDateStr);
			moneyService.updMoneyTransaction(transId, tranDate, comments,
					new BigDecimal(amountStr), SecurityUtil.getUserDetails()
							.getUser());

			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Update an existing monetary transition type
	 */
	@RequestMapping(value = "updMoneyTransitionType", method = RequestMethod.POST)
	public @ResponseBody StandardDto updMoneyTransitionType(
			@RequestParam(value = "transTypeId", required = true) String tranTypeIdStr,
			@RequestParam(value = "transTypeName", required = true) String tranTypeName) {
		StandardDto response = new StandardDto(false);
		try {
			long tranTypeId = NumberUtils.parseNumber(tranTypeIdStr, Long.class);
			logger.debug("updMoneyTransitionType>tranTypeIdStr = " + tranTypeIdStr);
			moneyService.updateMoneyTransTypeName(tranTypeId, tranTypeName,
					SecurityUtil.getUserDetails().getUser());

			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping(value = "/money", method = RequestMethod.GET)
	public ModelAndView showMoney(
			@RequestParam(value = "year", required = false) String strYear,
			@RequestParam(value = "month", required = false) String strMonth) {
		
		logger.info("money!");
		
		MyUserDetailsImpl ud = SecurityUtil.getUserDetails();

		ModelAndView mav = new ModelAndView("money.view");

		mav.addObject("user", ud.getUser());

		Calendar today = Calendar.getInstance();

		int year = (StringUtils.isBlank(strYear)) ? today.get(Calendar.YEAR) : NumberUtils.parseNumber(strYear, Integer.class);
		int month = (StringUtils.isBlank(strMonth)) ? today.get(Calendar.MONTH) : NumberUtils.parseNumber(strMonth, Integer.class);

		MonthlyMoneyDto monthlyMoney = moneyService.getMonthlyMoney(ud.getUser().getFamilyId(), year, month);

		mav.addObject("monthlyMoney", monthlyMoney);
		return mav;
	}

	@RequestMapping(value = "/getSingleTransition", method = RequestMethod.GET)
	public @ResponseBody MoneyTransactionDto getSingleTransition(String id) {

		MoneyTransactionBean transition = moneyService.getMoneyTransaction(SecurityUtil.getUserDetails().getUser().getFamilyId(), Long.parseLong(id));
		MoneyTransactionDto dto = new MoneyTransactionDto(transition);
		
		logger.debug("dto = " + dto);

		return dto;
	}

	@RequestMapping(value = "/getSingleTransitionType.json", method = RequestMethod.GET)
	public @ResponseBody MoneyTransactionTypeBean getSingleTransitionType(
			@RequestParam(value = "id", required = true) long id) {
		System.out.println("MoneyController.getSingleTransitionType> id = "
				+ id);
		MoneyTransactionTypeBean transitionType = moneyService.findTransactionTypeById(SecurityUtil.getUserDetails().getUser().getFamilyId(), id);
		System.out.println(transitionType);

		return transitionType;
	}

	@RequestMapping(value = "/getTransitionTypes", method = RequestMethod.GET)
	public @ResponseBody List<MoneyTransactionTypeBean> getTransitionTypes(
			@RequestParam(value = "ioe", required = true) String ioe) {

		MyUserDetailsImpl ud = SecurityUtil.getUserDetails();

		logger.debug("MoneyController.getTransationTypes> ioe = " + ioe
				+ ", familyId = " + ud.getUser().getFamilyId());
		List<MoneyTransactionTypeBean> transitionTypes = moneyService
				.findAvailableTransactionTypes(ud.getUser().getFamilyId(),
						IoeEnum.valueOf(ioe));

		System.out
				.println("MoneyController.getTransationTypes>transitionTypes.size() = "
						+ transitionTypes.size());
		for (MoneyTransactionTypeBean transitionType : transitionTypes) {
			System.out.println(transitionType);
		}

		return transitionTypes;
	}
	
	@RequestMapping(value = "/getActiveTransitionTypes.json", method = RequestMethod.GET)
	public @ResponseBody List<MoneyTransactionTypeBean> getActiveTransitionTypes(
			@RequestParam(value = "ioe", required = true) String ioe) {

		MyUserDetailsImpl ud = SecurityUtil.getUserDetails();

		logger.debug("MoneyController.getTransationTypes> ioe = " + ioe
				+ ", familyId = " + ud.getUser().getFamilyId());
		List<MoneyTransactionTypeBean> transitionTypes = moneyService
				.findAvailableTransactionTypes(ud.getUser().getFamilyId(),
						IoeEnum.valueOf(ioe));

		List<MoneyTransactionTypeBean> activeTransitionTypes = new ArrayList<MoneyTransactionTypeBean>();
		logger.debug("MoneyController.getActiveTransitionTypes>activeTransitionTypes.size() = "
						+ activeTransitionTypes.size());
		for (MoneyTransactionTypeBean transitionType : transitionTypes) {
			if (transitionType.getActive() == true)
			{
				activeTransitionTypes.add(transitionType);
				
			}
		}

		return activeTransitionTypes;
	}


	@RequestMapping(value = "deleteMoneyTransition", method = RequestMethod.POST)
	public @ResponseBody StandardDto deleteMoneyTransition(
			@RequestParam(value = "id", required = true) String id) {

		StandardDto response = new StandardDto(false);
		try {
			logger.info("id = " + id);
			moneyService.deleteMoneyTransition(Long.parseLong(id), SecurityUtil
					.getUserDetails().getUser());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	// /////////////////////////
	// Transaction Type

	@RequestMapping(value = "addMoneyTracType", method = RequestMethod.POST)
	public @ResponseBody StandardDto addMoneyTracType(
			@RequestParam(value = "ioe", required = true) String ioe,
			@RequestParam(value = "tracTypeName", required = true) String tracTypeName) {

		StandardDto response = new StandardDto(false);
		try {
			MyUserDetailsImpl ud = SecurityUtil.getUserDetails();

			List<MoneyTransactionTypeBean> transitionTypes = moneyService
					.findAvailableTransactionTypes(ud.getUser().getFamilyId(), IoeEnum.valueOf(ioe));

			boolean alreadyExist = false;
			for (MoneyTransactionTypeBean ftt : transitionTypes) {
				if (ftt.getName().trim().toUpperCase()
						.equals(tracTypeName.trim().toUpperCase())) {
					alreadyExist = true;
					break;
				}
			}
			if (!alreadyExist) {
				moneyService.addFamilyTracType(
						ud.getUser().getFamilyId(), ioe, tracTypeName);
				response.setSuccess(true);
			} else {
				response.setErrorMsg("The transaction " + tracTypeName
						+ " already exist!");
			}

		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;

	}

	@RequestMapping(value = "activateFamilyTracType", method = RequestMethod.POST)
	public @ResponseBody ActivateDto activateFamilyTracType(
			@RequestParam(value = "id", required = true) String id, 
			@RequestParam(value = "activate", required = true) String activateStr) {

		ActivateDto response = new ActivateDto();
		try {
			logger.debug("toggleFamilyTracType> id = " + id);
			boolean activate = Boolean.valueOf(activateStr);
			moneyService.activateFamilyTracType(Long.parseLong(id), SecurityUtil
					.getUserDetails().getUser(), activate);
			response.setActivated(true);
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

}
