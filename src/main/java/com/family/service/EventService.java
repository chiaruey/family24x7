package com.family.service;

import java.util.Date;

import com.family.service.bean.EventBean;
import com.family.service.bean.UserBean;

/**
 * Event Service for the whole family
 * 
 * @author clu3
 */
public interface EventService {
	
	void addNewEvent(UserBean user, Date startTime, Date endTime, String title, String description, Boolean allDay);
	
	void updateEvent(UserBean user, long id,  Date startTime, Date endTime, String title, String description, Boolean allDay);

	void deleteEvent(UserBean user, long id);
	
	EventBean getEvent(long id);
}
