package com.family.service;

import com.family.service.bean.FamilyBean;
import com.family.web.dto.CalendarDayDto;
import com.family.web.dto.CalendarMonthDto;
import com.family.web.dto.CalendarWeekDto;

public interface CalendarService {
	
	CalendarMonthDto getCalendarMonth(FamilyBean family, int year, int month, int day);
	
	CalendarWeekDto getCalendarWeek(FamilyBean family, int year, int month, int day);
	
	CalendarDayDto getCalendarDay(FamilyBean family, int year, int month, int day);

}
