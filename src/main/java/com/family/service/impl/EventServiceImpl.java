package com.family.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.family.db.dao.EventDao;
import com.family.db.domain.EventDomain;
import com.family.enums.SectionEnum;
import com.family.service.AccountService;
import com.family.service.EventService;
import com.family.service.WallService;
import com.family.service.bean.EventBean;
import com.family.service.bean.FamilyBean;
import com.family.service.bean.UserBean;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventDao eventDao;
	
	@Autowired
	private WallService wallService;	
	
	@Autowired
	private AccountService accountService;

	@Transactional
	public void addNewEvent(UserBean user, Date startTime, Date endTime, String title, String description, Boolean allDay) {	
		eventDao.addNewEvent(String.valueOf(user.getFamilyId()), startTime, endTime, title, description, allDay);
		String message = user.getFirstName() + " has added new event : " + title ;	
		wallService.addWallMessage(user, SectionEnum.Calendar, message);	
	}
	

	@Transactional
	public void updateEvent(UserBean user, long id, Date startTime, Date endTime, String title, String description, Boolean allDay) {
		eventDao.updateEvent(String.valueOf(id), startTime, endTime, title, description, allDay);
		String message = user.getFirstName() + " has updated the event : " + title ;	
		wallService.addWallMessage(user, SectionEnum.Calendar, message);	
		
	}
	
	@Transactional
	public void deleteEvent(UserBean user, long id) {
		EventDomain event = eventDao.findEvent(String.valueOf(id));
		
		if (event != null) {
			String message = user.getFirstName() + " has deleted the event : " + event.getTitle() ;	
			wallService.addWallMessage(user, SectionEnum.Calendar, message);	
			
			eventDao.deleteEvent(String.valueOf(id));			
		}

	}

	@Transactional
	public EventBean getEvent(long id) {
		EventDomain eventDomain = eventDao.findEvent(String.valueOf(id));
		EventBean eventBean = new EventBean(eventDomain);
		FamilyBean familyBean = accountService.findFamily(Long.valueOf(eventDomain.getFamilyId()));
		eventBean.setFamily(familyBean);
		return eventBean;
	}

}
