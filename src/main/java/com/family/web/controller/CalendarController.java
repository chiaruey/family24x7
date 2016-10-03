package com.family.web.controller;

import java.util.Calendar;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.family.security.SecurityUtil;
import com.family.security.MyUserDetailsImpl;
import com.family.service.AccountService;
import com.family.service.CalendarService;
import com.family.service.bean.FamilyBean;
import com.family.web.dto.CalendarDayDto;
import com.family.web.dto.CalendarMonthDto;
import com.family.web.dto.CalendarWeekDto;

@Controller
public class CalendarController {
	private Logger  logger = Logger.getLogger("com.family.web.controller");
	
	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private AccountService accountService;

	
	public CalendarController() {
		logger.setLevel(Level.DEBUG);
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value="/calendar", method=RequestMethod.GET)
	public ModelAndView showCalendarMonth(
			@RequestParam(value = "dateString", required = false) String dateString) {
		logger.info("showCalendar!");	
		ModelAndView mav = new ModelAndView("calendar.view");
		MyUserDetailsImpl ud = SecurityUtil.getUserDetails();
		FamilyBean family = accountService.findFamily(Long.valueOf(ud.getUser().getFamilyId()));

		mav.addObject("user", ud.getUser());
		mav.addObject("family", family);
		CalendarMonthDto monCalendar = null;
		if (StringUtils.isNotBlank(dateString)) {
			StringTokenizer stk = new StringTokenizer(dateString, "-");
			int year = Integer.valueOf(stk.nextToken());
			int month = Integer.valueOf(stk.nextToken()) - 1;
			int day = Integer.valueOf(stk.nextToken());
			monCalendar = calendarService.getCalendarMonth(family, year, month, day);
		} else {
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH);
			int day = now.get(Calendar.DAY_OF_MONTH);	
			monCalendar = calendarService.getCalendarMonth(family, year, month, day);
		}
	
		mav.addObject("monCalendar", monCalendar);
		return mav;		
	}
	
	@RequestMapping(value="/calendarWeek", method=RequestMethod.GET)
	public ModelAndView showCalendarWeek(
			@RequestParam(value = "dateString", required = true) String dateString) {
		logger.info("calendarWeek > dateString = " + dateString);		
		
		ModelAndView mav = new ModelAndView("calendar.week.view");
		MyUserDetailsImpl ud = SecurityUtil.getUserDetails();
		mav.addObject("user", ud.getUser());
		FamilyBean family = accountService.findFamily(ud.getUser().getFamilyId());
		mav.addObject("family", family);
		CalendarWeekDto weekCalendar = null;
		
		if (StringUtils.isNotBlank(dateString)) {
			StringTokenizer stk = new StringTokenizer(dateString, "-");
			int year = Integer.valueOf(stk.nextToken());
			int month = Integer.valueOf(stk.nextToken()) - 1;
			int day = Integer.valueOf(stk.nextToken());
			weekCalendar = calendarService.getCalendarWeek(family, year, month, day);
		} else {
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH);
			int day = now.get(Calendar.DAY_OF_MONTH);	
			weekCalendar = calendarService.getCalendarWeek(family, year, month, day);
		}

		mav.addObject("weekCalendar", weekCalendar);
		return mav;		
	}

	/**
	 * Input String: 2014-10-06-Mon
	 * 
	 * @return
	 */
	@RequestMapping(value="/calendarDay", method=RequestMethod.GET)
	public ModelAndView showCalendarDay(
			@RequestParam(value = "dateString", required = true) String dateString) {
		logger.info("showCalendarDay > dateString = " + dateString);

		ModelAndView mav = new ModelAndView("calendar.day.view");
		MyUserDetailsImpl ud = SecurityUtil.getUserDetails();
		FamilyBean family = accountService.findFamily(Long.valueOf(ud.getUser().getFamilyId()));

		mav.addObject("user", ud.getUser());
		mav.addObject("family", family);
		
		CalendarDayDto dayCalendar = null;
		
		if (StringUtils.isNotBlank(dateString)) {
			StringTokenizer stk = new StringTokenizer(dateString, "-");
			int year = Integer.valueOf(stk.nextToken());
			int month = Integer.valueOf(stk.nextToken()) - 1;
			int day = Integer.valueOf(stk.nextToken());
			dayCalendar = calendarService.getCalendarDay(family, year, month, day);
		} else {
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH);
			int day = now.get(Calendar.DAY_OF_MONTH);	
			dayCalendar = calendarService.getCalendarDay(family, year, month, day);
		}


		mav.addObject("dayCalendar", dayCalendar);
		return mav;		
	}


}
