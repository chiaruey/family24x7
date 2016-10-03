package com.family.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.family.db.dao.EventDao;
import com.family.db.domain.EventDomain;
import com.family.db.util.DbUtils;
import com.family.exception.ServiceException;
import com.family.service.AccountService;
import com.family.service.CalendarService;
import com.family.service.bean.EventBean;
import com.family.service.bean.FamilyBean;
import com.family.web.dto.CalendarDayDto;
import com.family.web.dto.CalendarMonthDto;
import com.family.web.dto.CalendarWeekDto;
import com.family.web.dto.EventDto;
import com.family.web.util.MyCalendarUtils;

import static com.family.web.util.MyCalendarUtils.*;

@Service
public class CalendarServiceImpl implements CalendarService {
	
	@Autowired
	private EventDao eventDao;
	
	@Autowired
	private AccountService accountService;
	
	@Transactional
	public CalendarMonthDto getCalendarMonth(FamilyBean family, int year, int month, int day) {
		
		CalendarMonthDto monthCalendar = new CalendarMonthDto(new GregorianCalendar(year, month, day));

		Date monthFromTime = new GregorianCalendar(monthCalendar.getFirstDay().get(Calendar.YEAR), monthCalendar.getFirstDay().get(Calendar.MONTH), monthCalendar.getFirstDay().get(Calendar.DAY_OF_MONTH), 0, 0, 0).getTime();
		Date monthToTime = new GregorianCalendar(monthCalendar.getLastDay().get(Calendar.YEAR), monthCalendar.getLastDay().get(Calendar.MONTH), monthCalendar.getLastDay().get(Calendar.DAY_OF_MONTH), 23, 59, 59).getTime();
		
		List<EventDomain> eventDomainList = eventDao.findFamilyEvents(String.valueOf(family.getId()), monthFromTime, monthToTime);
		
		if (!CollectionUtils.isEmpty(eventDomainList)) {
			for (EventDomain eventDomain: eventDomainList) {
				System.out.println("getCalendarMonth> " + eventDomain.getTitle() + ", Event Start Time = " + eventDomain.getStartTime() + ", Event End Time = " + eventDomain.getEndTime() + " <<<<<<<<<<< ");
				EventBean event = new EventBean(eventDomain);
				event.setFamily(family);
				for (Map<Integer, CalendarDayDto> calendarDayMap : monthCalendar.getCalendarWeekList()) {
					for (CalendarDayDto calendarDay : calendarDayMap.values()) {
						try {
							if (MyCalendarUtils.isEventDay(calendarDay.getCalendar().getTime(), event.getStartTime(), event.getEndTime())) {
								calendarDay.getEventList().add(new EventDto(event));
								System.out.println(" >>>> During Event " + calendarDay.getDateString());
							} else {
								System.out.println(" ***** NOT in Event " + calendarDay.getDateString());
							}
							
						} catch(Exception e) {
							e.printStackTrace();
							throw new ServiceException("Fail to Parse CalendarDay" + e.getMessage(), e);
						}		
					}
		
				}
			}			
		}

		return monthCalendar;
	}
	
	@Transactional
	public CalendarWeekDto getCalendarWeek(FamilyBean family, int year, int month, int day) {
		CalendarWeekDto weekCalendar = new CalendarWeekDto(year, month, day);
		
		Date weekFromTime = new GregorianCalendar(weekCalendar.getFirstDay().get(Calendar.YEAR), weekCalendar.getFirstDay().get(Calendar.MONTH), weekCalendar.getFirstDay().get(Calendar.DAY_OF_MONTH), 0, 0, 0).getTime();
		Date weekToTime = new GregorianCalendar(weekCalendar.getLastDay().get(Calendar.YEAR), weekCalendar.getLastDay().get(Calendar.MONTH), weekCalendar.getLastDay().get(Calendar.DAY_OF_MONTH), 23, 59, 59).getTime();
		
		List<EventDomain> eventList = eventDao.findFamilyEvents(String.valueOf(family.getId()), weekFromTime, weekToTime);
		
		if (!CollectionUtils.isEmpty(eventList)) {
			Collections.sort(eventList);
			
			for (EventDomain eventDomain: eventList) {
				
				System.out.println("CalendarWeekDto> " + eventDomain.getTitle() + ", Event Start Time = " + eventDomain.getStartTime() + ", Event End Time = " + eventDomain.getEndTime() + " <<<<<<<<<<< ");

				for (Entry<String, CalendarDayDto> entry : weekCalendar.getCalendarDayMap().entrySet()) {
					try {
						Date date = WEEKLY_CELL_SDF.parse(entry.getKey());
						
						if (MyCalendarUtils.isEventDay(date, DbUtils.decodeDate(eventDomain.getStartTime()), DbUtils.decodeDate(eventDomain.getEndTime()))) {
							EventBean eventBean = new EventBean(eventDomain);
							entry.getValue().getEventList().add(new EventDto(eventBean));
							System.out.println(" >>>> During Event " + entry.getValue().getDateString());
						} else {
							System.out.println(" ***** NOT in Event " + entry.getValue().getDateString());
						}
						
					} catch(Exception e) {
						e.printStackTrace();
						throw new ServiceException("Fail to Parse CalendarDay" + e.getMessage(), e);
					}		
				}
			}
			
		}
		
		return weekCalendar;
	}
	
	
	@Transactional
	public CalendarDayDto getCalendarDay(FamilyBean family, int year, int month, int day) {	
		CalendarDayDto dayCalendar = new CalendarDayDto(year, month, day);
		
		List<EventDto> eventDtoList = this.getDailyEventList(family, year, month, day);
		dayCalendar.setEventList(eventDtoList);
		
		return dayCalendar;		
	}
	
	private List<EventDto> getDailyEventList(FamilyBean family, int year, int month, int day) {
		Calendar fromTime = new GregorianCalendar(year, month, day, 0, 0, 0);
		Calendar toTime = new GregorianCalendar(year, month, day, 23, 59, 59);
		
		List<EventDomain> eventDomainList = eventDao.findFamilyEvents(String.valueOf(family.getId()), fromTime.getTime(), toTime.getTime());

		List<EventDto> eventDtoList = new ArrayList<EventDto>();
		if (!CollectionUtils.isEmpty(eventDomainList)) {
			Collections.sort(eventDomainList);
			for (EventDomain eventDomain : eventDomainList) {
				eventDtoList.add(new EventDto(new EventBean(eventDomain)));
			}		
		}	
		
		return eventDtoList;
	}
	
	

}
