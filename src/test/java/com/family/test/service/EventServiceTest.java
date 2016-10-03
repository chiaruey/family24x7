package com.family.test.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.family.config.AppConfig;
import com.family.service.AccountService;
import com.family.service.EventService;
import com.family.service.bean.EventBean;


import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { AppConfig.class})
public class EventServiceTest {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private EventService eventService;
	
	// void addNewEvent(UserBean user, Date startTime, Date endTime, String title, String description, Boolean allDay);
	
	private Logger  logger = Logger.getLogger("com.family.test.service");
	
	
	
	@Test
	public void testGetEvent() {
		long eventId = 150507063823026L;
		EventBean eventBean = null;
		try {
			eventBean = eventService.getEvent(eventId);
			logger.debug("testGetEvent>" + eventBean);
		} catch(Exception e) {
			logger.error("Fail to get event", e);
		}
		assertNotNull(eventBean);
	}
		

}
