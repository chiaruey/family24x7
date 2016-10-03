package com.family.web.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.family.web.util.MyCalendarUtils;

import static com.family.web.util.MyCalendarUtils.*;

public class CalendarWeekDto extends StandardDto implements Serializable {

	private static final long serialVersionUID = -2302108066719206278L;

	private String weekDaysString;
	private String prevWeekString;
	private String nextWeekString;
	private String dateString;

	private int month;
	private int year;

	private Map<String, CalendarDayDto> calendarDayMap;
	
	private String firstDayString;
	private String lastDayString;

	private Calendar firstDay;
	private Calendar lastDay;
	
	private Set<String> timeSet;
	
	public CalendarWeekDto(Calendar calendar) {
		
		this.month = calendar.get(Calendar.MONTH);
		this.year = calendar.get(Calendar.YEAR);
			
		/*DateTimeFormatter dateStringSdf = DateTimeFormat.forPattern("yyyy-MM-dd");*/
		DateTime current = new DateTime(calendar);
		this.dateString = DATE_FORMATTER.print(current);			

		DateTime firstDayTime;
		
		DateTime lastDayTime;

		if (current.getDayOfWeek() == DateTimeConstants.SUNDAY) {
			firstDayTime = current;
			lastDayTime = current.plusWeeks(1).withDayOfWeek(DateTimeConstants.SATURDAY);
		} else {
			firstDayTime = current.minusWeeks(1).withDayOfWeek(DateTimeConstants.SUNDAY);
			lastDayTime = current.withDayOfWeek(DateTimeConstants.SATURDAY);
		}

		Calendar sun = firstDayTime.toCalendar(Locale.US);
		Calendar sat = lastDayTime.toCalendar(Locale.US);
		this.firstDay = sun;
		this.lastDay = sat;
		
		this.firstDayString = DATE_FORMATTER.print(firstDayTime);
		this.lastDayString = DATE_FORMATTER.print(lastDayTime);

		if (sun.get(Calendar.YEAR) != sat.get(Calendar.YEAR)) {
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yy");
			weekDaysString = sdf.format(sun.getTime()) + " - "
					+ sdf.format(sat.getTime());
		} else if (sun.get(Calendar.MONTH) != sat.get(Calendar.MONTH)) {
			weekDaysString = (new SimpleDateFormat("MMM dd")).format(sun
					.getTime())
					+ " - "
					+ new SimpleDateFormat("MMM dd, yy").format(sat.getTime());
		} else {
			weekDaysString = new SimpleDateFormat("MMM dd").format(sun
					.getTime())
					+ " - "
					+ new SimpleDateFormat("dd, yy").format(sat.getTime());
		}

		this.nextWeekString = DATE_FORMATTER.print(firstDayTime.plusWeeks(1));

		Calendar prevSunday = (Calendar) sun.clone();
		prevSunday.add(Calendar.DATE, -7);
		this.prevWeekString = DATE_FORMATTER.print(firstDayTime.minusWeeks(1));
		
		this.timeSet = MyCalendarUtils.getDailyHHmmList();

		populateWeekEventMap(sun);
	}

	
	public Set<String> getTimeSet() {
		return timeSet;
	}


	public CalendarWeekDto(int sunYear, int sunMonth, int sunDayOfMonth) {
		this(new GregorianCalendar(sunYear, sunMonth, sunDayOfMonth));
	}

	public static void main(String[] args) {

		CalendarWeekDto dto = new CalendarWeekDto(Calendar.getInstance());
		System.out.print(dto);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getWeekDaysString() {
		return weekDaysString;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public String getPrevWeekString() {
		return prevWeekString;
	}

	public String getNextWeekString() {
		return nextWeekString;
	}
	

	public Map<String, CalendarDayDto> getCalendarDayMap() {
		return calendarDayMap;
	}

	
	public void setCalendarDayMap(Map<String, CalendarDayDto> calendarDayMap) {
		this.calendarDayMap = calendarDayMap;
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


	private void populateWeekEventMap(Calendar calendarParam) {
		this.calendarDayMap = new LinkedHashMap<String, CalendarDayDto>();
		
		for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
			Calendar calendar = (Calendar) calendarParam.clone();
			
			if (i > 1) {
				calendar.add(Calendar.DATE, i - 1);
			}
			
			String key = WEEKLY_CELL_SDF.format(calendar.getTime());
			System.out.println("Add to calendar: " + key);
		
			this.calendarDayMap.put(WEEKLY_CELL_SDF.format(calendar.getTime()),
					new CalendarDayDto(calendar));
		}

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
