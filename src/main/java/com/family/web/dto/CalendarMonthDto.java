package com.family.web.dto;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.family.web.util.MyCalendarUtils;

/**
 * This class abstract the data to populate a Calendar month
 * 
 * @author Daddy
 *
 */
public class CalendarMonthDto {
	private List<Map<Integer, CalendarDayDto>> calendarWeekList;
	private String year;
	private String month;
	private String day;
	private String monthName;
	private String monthAbbr;
	
	private String firstDayString;
	private String lastDayString;
	
	private Calendar firstDay;
	private Calendar lastDay;
	
	private String prevMonthString;
	private String nextMonthString;
	
	private Calendar selectedDate;
	private String dateString;
	private Set<String> timeSet;
	
	public static void main(String[] args) {
		Calendar calendar = new GregorianCalendar(2015, 1, 1);	
		CalendarMonthDto dto = new CalendarMonthDto(calendar);		
		System.out.println(dto);
	}
	
	public CalendarMonthDto(Calendar calendar)
	{
		DateTime current = new DateTime(calendar);
		DateTimeFormatter dateStringSdf = DateTimeFormat.forPattern("yyyy-MM-dd");
		this.dateString = dateStringSdf.print(current);
		
		this.year = current.toString("yyyy"); 
		this.month =  current.toString("MM");  
		this.day = String.valueOf(current.getDayOfMonth()); 
		this.selectedDate = calendar;
		this.monthName = current.toString("MMMMM");
		this.monthAbbr = current.toString("MMM");
		
		calendarWeekList = MyCalendarUtils.getCalendarWeekList(calendar);
		
		// Previous month
		this.prevMonthString =  dateStringSdf.print(current.plusMonths(-1));
		
		// Next month
		this.nextMonthString = dateStringSdf.print(current.plusMonths(1));
		
		DateTime lastDateTime = current.dayOfMonth().withMaximumValue();
		
		this.lastDay = lastDateTime.toCalendar(Locale.US);
		
		
		lastDayString = dateStringSdf.print(lastDateTime);
		
		DateTime firstDateTime = current.dayOfMonth().withMinimumValue();
		
		this.firstDay = firstDateTime.toCalendar(Locale.US);
		
		firstDayString = dateStringSdf.print(firstDateTime);
		
		this.timeSet = MyCalendarUtils.getDailyHHmmList();
	}

	
	public Set<String> getTimeSet() {
		return timeSet;
	}

	public String getYear() {
		return year;
	}

	public String getMonth() {
		return month;
	}

	public String getDay() {
		return day;
	}

	public Calendar getSelectedDate() {
		return selectedDate;
	}

	public String getMonthName() {
		return monthName;
	}


	public String getMonthAbbr() {
		return monthAbbr;
	}

	public String getPrevMonthString() {
		return prevMonthString;
	}

	public String getNextMonthString() {
		return nextMonthString;
	}

	public List<Map<Integer, CalendarDayDto>> getCalendarWeekList() {
		return calendarWeekList;
	}

	
	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	

	public String getFirstDayString() {
		return firstDayString;
	}

	public String getLastDayString() {
		return lastDayString;
	}
	
	public Calendar getFirstDay() {
		return firstDay;
	}

	public Calendar getLastDay() {
		return lastDay;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


}
