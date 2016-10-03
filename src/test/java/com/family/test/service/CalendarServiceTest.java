package com.family.test.service;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.family.config.AppConfig;
import com.family.service.AccountService;
import com.family.service.CalendarService;
import com.family.service.bean.FamilyBean;
import com.family.service.bean.UserBean;
import com.family.web.dto.CalendarWeekDto;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { AppConfig.class})
public class CalendarServiceTest {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CalendarService calendarService;
	
	private Logger  logger = Logger.getLogger("com.family.test.service");

	
	// getCalendarWeek
	@Test
	public void testGetCalendarWeek() {
		
		String username = "barrackobama";
		
		UserBean user = accountService.findUserByUsername(username);
		FamilyBean family = accountService.findFamily(user.getFamilyId());
		
		logger.debug("testGetCalendarWeek> user = " + user);
		logger.debug("testGetCalendarWeek> family = " + family);
		
		int year = 2015;
		int month = Calendar.MAY;
		int day = 8;
		CalendarWeekDto calendarWeek = null;
		
		try {
			calendarWeek = calendarService.getCalendarWeek(family, year, month, day);
			logger.debug("testGetCalendarWeek> calendarWeek = " + calendarWeek);
		} catch(Exception e) {
			logger.error("fail on getCalendarWeek", e);
		}
		assertNotNull(calendarWeek);
		
	}
	
}
