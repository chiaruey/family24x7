package com.family.web.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.web.util.MyCalendarUtils;

/**
 * This class contains data to be populated in a date cell in the calendar table
 * of the calendar view
 * 
 * @author Daddy
 */
public class CalendarDayDto extends StandardDto implements Serializable {

	private static final long serialVersionUID = 8877050247944882770L;

	private int dayOfWeek;
	private String dayOfMonth;
	private String month;
	private String monthName;
	private String monthAbbr;
	private String dayName;

	private int year;

	private String dateString;
	private String sundayDateString;

	private String yesterdayMonth;
	private String yesterdayYear;
	private String yesterdayMonthName;
	private String yesterdayDayOfMonth;
	private int yesterdayDayOfWeek;
	private String yesterdayDateString;
	
	private String tomorrowMonth;
	private String tomorrowYear;
	private String tomorrowMonthName;
	private String tomorrowDayOfMonth;
	private int tomorrowDayOfWeek;
	private String tomorrowDateString;
	
	private List<EventDto> eventList;
	
	private Set<String> timeSet;
	
	private Calendar calendar;
	
	private boolean isToday;
	

	public CalendarDayDto(Calendar calendarParam) {
		this.calendar = (Calendar) calendarParam.clone();
		
		
		Calendar tempCalendar = (Calendar) calendarParam.clone();
		SimpleDateFormat monthNameSdf = new SimpleDateFormat("MMMMM", Locale.US);
		SimpleDateFormat monthAbbrSdf = new SimpleDateFormat("MMM");
		SimpleDateFormat dayNameSdf = new SimpleDateFormat("EEE");
		SimpleDateFormat dateStringSdf = new SimpleDateFormat("yyyy-MM-dd");
	
		this.monthName = monthNameSdf.format(tempCalendar.getTime());
		this.monthAbbr = monthAbbrSdf.format(tempCalendar.getTime());

		this.dayName = dayNameSdf.format(tempCalendar.getTime());
		this.year = tempCalendar.get(Calendar.YEAR);
		this.month = String.valueOf(tempCalendar.get(Calendar.MONTH));
		this.dayOfMonth = String.valueOf(tempCalendar.get(Calendar.DAY_OF_MONTH));
		this.dayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);
		
		this.dateString = dateStringSdf.format(tempCalendar.getTime());
		isToday = dateStringSdf.format(Calendar.getInstance().getTime()).equals(this.dateString);
		
		Calendar sunday = (Calendar)tempCalendar.clone();
		
		if (sunday.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			sunday.add(Calendar.DATE, sunday.get(Calendar.DAY_OF_MONTH) - Calendar.SUNDAY);
			this.sundayDateString = dateStringSdf.format(sunday.getTime());
		}

		// yesterday
		tempCalendar.add(Calendar.DATE, -1);
		this.yesterdayMonth = String.valueOf(tempCalendar.get(Calendar.MONTH));
		this.yesterdayMonthName = monthNameSdf.format(tempCalendar.getTime());
		this.yesterdayYear = String.valueOf(tempCalendar.get(Calendar.YEAR));
		this.yesterdayDayOfMonth = String.valueOf(tempCalendar.get(Calendar.DAY_OF_MONTH));
		this.yesterdayDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);
		this.yesterdayDateString = dateStringSdf.format(tempCalendar.getTime());
		
		// tomorrow
		tempCalendar.add(Calendar.DATE, 2);
		this.tomorrowMonth = String.valueOf(tempCalendar.get(Calendar.MONTH));
		this.tomorrowMonthName = monthNameSdf.format(tempCalendar.getTime());
		this.tomorrowYear = String.valueOf(tempCalendar.get(Calendar.YEAR));	
		this.tomorrowDayOfMonth = String.valueOf(tempCalendar.get(Calendar.DAY_OF_MONTH));
		this.tomorrowDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);
		this.tomorrowDateString = dateStringSdf.format(tempCalendar.getTime());		
		this.timeSet = MyCalendarUtils.getDailyHHmmList();
		this.eventList = new ArrayList<EventDto>();

	}

	public CalendarDayDto(int year, int month, int day) {
		this(new GregorianCalendar(year, month, day));
	}
	
	public static void main(String[] args) {
		CalendarDayDto dto = new CalendarDayDto(Calendar.getInstance());
		
		System.out.println(dto);	
	}
	

	public Calendar getCalendar() {
		return calendar;
	}

	public Set<String> getTimeSet() {
		return timeSet;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public String getDayOfMonth() {
		return dayOfMonth;
	}

	public String getMonth() {
		return month;
	}

	public String getMonthName() {
		return monthName;
	}

	public String getDayName() {
		return dayName;
	}

	public int getYear() {
		return year;
	}

	public String getDateString() {
		return dateString;
	}
	
	public String getYesterdayMonth() {
		return yesterdayMonth;
	}

	public String getYesterdayYear() {
		return yesterdayYear;
	}

	public String getYesterdayMonthName() {
		return yesterdayMonthName;
	}

	public String getYesterdayDayOfMonth() {
		return yesterdayDayOfMonth;
	}

	public int getYesterdayDayOfWeek() {
		return yesterdayDayOfWeek;
	}

	public String getYesterdayDateString() {
		return yesterdayDateString;
	}

	public String getTomorrowMonth() {
		return tomorrowMonth;
	}

	public String getTomorrowYear() {
		return tomorrowYear;
	}

	public String getTomorrowMonthName() {
		return tomorrowMonthName;
	}

	public String getTomorrowDayOfMonth() {
		return tomorrowDayOfMonth;
	}

	public int getTomorrowDayOfWeek() {
		return tomorrowDayOfWeek;
	}

	public String getTomorrowDateString() {
		return tomorrowDateString;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSundayDateString() {
		return sundayDateString;
	}
	
	public String getMonthAbbr() {
		return monthAbbr;
	}

	public List<EventDto> getEventList() {
		return eventList;
	}

	public void setEventList(List<EventDto> eventList) {
		this.eventList = eventList;
	}

	public boolean isToday() {
		return isToday;
	}

	public void setToday(boolean isToday) {
		this.isToday = isToday;
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
