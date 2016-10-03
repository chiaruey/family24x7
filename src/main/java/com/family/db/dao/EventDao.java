package com.family.db.dao;

import java.util.Date;
import java.util.List;

import com.family.db.domain.EventDomain;

public interface EventDao {
	List<EventDomain> findFamilyEvents(String familyId, Date fromTime, Date toTime);
	
	EventDomain addNewEvent(String familyId, Date startTime, Date endTime, String title, String description, Boolean allDay);
	
	EventDomain updateEvent(String id, Date startTime, Date endTime, String title, String description, Boolean allDay);

	void deleteEvent(String id);
	
	EventDomain findEvent(String id);
}
