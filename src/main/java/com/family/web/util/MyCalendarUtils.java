package com.family.web.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.family.util.IntDescComparator;
import com.family.web.dto.CalendarDayDto;

public class MyCalendarUtils {
	
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
	
	public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd 'at' HH:mm");
	
	
	public static final SimpleDateFormat WEEKLY_CELL_SDF = new SimpleDateFormat("EEE (MM/dd/yy)");
	
	private static Logger logger = Logger.getLogger(MyCalendarUtils.class);

	public static void main(String[] args) {
		Calendar dob = new GregorianCalendar(1966, 5, 12);
		
		System.out.println("My Age is " + getAge(dob.getTime()));
	}

	public static String printDateTime(Date date) {
		String dateTimeString = DATETIME_FORMATTER.print(new DateTime(date));
		
		return dateTimeString;
	}
	

	
	public static Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Date getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static boolean isEventDay(Date theDay, Date eventStartTime, Date eventEndTime) {
		Date startOfDay = getStartOfDay(theDay);
		Date endOfDay = getEndOfDay(theDay);
		boolean notEventDay = startOfDay.after(eventEndTime) || endOfDay.before(eventStartTime);
		
		return !notEventDay;
	}

	public static Set<String> getDailyHHmmList() {
		DateTime startTime = new DateTime(2005, 1, 1, 0, 0, 0, 0);
		DateTime endTime = new DateTime(2005, 1, 2, 0, 0, 0, 0);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
		Set<String> timeList = new LinkedHashSet<String>();
		do {
			String timeString = fmt.print(startTime);
			timeList.add(timeString);
			startTime = startTime.plusMinutes(15);
		} while (startTime.getMillis() < endTime.getMillis());
	
		return timeList;
	}
	
	/**
	 * Returns days list in for specified year/month (Include February)
	 */
	public static Set<Integer> getDayList(int year, int month) {
	
		GregorianCalendar calendar = new GregorianCalendar(year, month, 1);
		int daysMax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		Set<Integer> days = new TreeSet<Integer>();
		for (int i=1; i<=daysMax; i++) {
			days.add(i);
		}
		return days;
	}
	
	
	/**
	 * Return the year list counted from this year
	 */
	public static Set<Integer> getYearList(int numYears) {
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		Set<Integer> years = new TreeSet<Integer>(new IntDescComparator());
		for (int i=0; i<=numYears; i++) {
			years.add(thisYear - i);
		}
		
		return years;
	}
	

	/**
	 * Return month list for the whole year
	 */
	public static Set<Integer> getMonthList() {
		Set<Integer> months = new TreeSet<Integer>();
		months.add(Calendar.JANUARY);
		months.add(Calendar.FEBRUARY);
		months.add(Calendar.MARCH);
		months.add(Calendar.APRIL);
		months.add(Calendar.MAY);
		months.add(Calendar.JUNE);
		months.add(Calendar.JULY);
		months.add(Calendar.AUGUST);
		months.add(Calendar.SEPTEMBER);
		months.add(Calendar.OCTOBER);
		months.add(Calendar.NOVEMBER);
		months.add(Calendar.DECEMBER);
		
		return months;
	}
	
	/**
	 * Return the a list of number 1-31
	 */
	public static Set<Integer> getDayList() {
		Set<Integer> days = new TreeSet<Integer>();
		for (int i=1; i<=31; i++) {
			days.add(i);
		}
		
		return days;
	}

	/**
	 * Return the age on the provided birthDay
	 */
	public static int getAge(Date birthDay) {
		int age = 0;
		
		long ageMills = Calendar.getInstance().getTimeInMillis() - birthDay.getTime();
		
		long milliSecondPerYr = (long) 1000 * 365 * 24 * 60 * 60;
		
		boolean isEven = (ageMills % milliSecondPerYr) == 0;
		
		age = ( (int) (ageMills / milliSecondPerYr)) + ((isEven) ? 0 : 1);

		return age;
	}
	
	
	
	public static String printDayOfWeek(int dayOfWeek) {
		String strDayOfWeek = "SUNDAY";
		
		if (dayOfWeek == 2) {
			strDayOfWeek = "MONDAY";
		} else if (dayOfWeek == 3) {
			strDayOfWeek = "TUESDAY";			
		} else if (dayOfWeek == 4) {
			strDayOfWeek = "WEDNESDAY";
		} else if (dayOfWeek == 5) {
			strDayOfWeek = "THURSDAY";
		} else if (dayOfWeek == 6) {
			strDayOfWeek = "FRIDAY";
		} else if (dayOfWeek == 7) {
			strDayOfWeek = "SATURDAY";
		} 

		
		return strDayOfWeek;
	}
	
	/**
	 * It returns a ArrayList which contains the date objects from week 1 to week 5
	 */
	public static List<Map<Integer, CalendarDayDto>> getCalendarWeekList(Calendar selectedDate) {
		
		int year = selectedDate.get(Calendar.YEAR);
		int month = selectedDate.get(Calendar.MONTH);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd EEEEE");
		
		List<Map<Integer, CalendarDayDto>> calendarList = new ArrayList<Map<Integer, CalendarDayDto>>();
		
		///////////////////
		// The first week
		
		Calendar firstDayOfMonth = new GregorianCalendar(year, month, 1);
			
		Map<Integer, CalendarDayDto> firstWeek = new HashMap<Integer, CalendarDayDto>();
		firstWeek.put(firstDayOfMonth.get(Calendar.DAY_OF_WEEK), new CalendarDayDto(firstDayOfMonth));
		
		// Get the last month
		if (firstDayOfMonth.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {			
			int lastMonthDays = firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			Calendar lastMonthDay = firstDayOfMonth;
			for (int i=1; i<=lastMonthDays; i++) {
				lastMonthDay.add(Calendar.DATE, -1);
				logger.debug("Put into first Week= " + sdf.format(lastMonthDay.getTime()));
				firstWeek.put(lastMonthDay.get(Calendar.DAY_OF_WEEK), new CalendarDayDto(lastMonthDay));
			}
		}
		
		Calendar currentDate;
		// Get the rest of days for the first week
		if (firstDayOfMonth.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {			
			int firstWeekDays = Calendar.SATURDAY - firstDayOfMonth.get(Calendar.DAY_OF_WEEK);
			Calendar firstWeekDay = firstDayOfMonth;
			for (int i=1; i<=firstWeekDays; i++) {
				firstWeekDay.add(Calendar.DATE, 1);
				firstWeek.put(firstWeekDay.get(Calendar.DAY_OF_WEEK), new CalendarDayDto(firstWeekDay));
			}
			currentDate = firstWeekDay;
		} else {
			currentDate = firstDayOfMonth;
		}
		
		logger.debug("lastDateOfFirstWeek = " + sdf.format(currentDate.getTime()));
		calendarList.add(firstWeek);
		
		////////////////////////////////////
		// The full weeks
		int lastDateOfMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		logger.debug("lastDateOfMonth = " + lastDateOfMonth);
		
		int restOfFullWeeks = (lastDateOfMonth - currentDate.get(Calendar.DATE))/7;
		logger.debug("sevendaysWeek = " + restOfFullWeeks);
		
		for (int i=2; i<=(restOfFullWeeks+1); i++) {
			Map<Integer, CalendarDayDto> oneWeek = new HashMap<Integer, CalendarDayDto>();
			for (int j=1; j<=7; j++) {
				currentDate.add(Calendar.DATE, 1);
				oneWeek.put(currentDate.get(Calendar.DAY_OF_WEEK), new CalendarDayDto(currentDate));
			}
			logger.debug("lastDate of week " + i + " = "+ sdf.format(currentDate.getTime()));
			calendarList.add(oneWeek);
		}

		
		//////////////////////////////////////
		// The last Week 
		if (currentDate.get(Calendar.DAY_OF_MONTH) < lastDateOfMonth) {
			Map<Integer, CalendarDayDto> lastWeek = new HashMap<Integer, CalendarDayDto>();
			int remainingDays = lastDateOfMonth - currentDate.get(Calendar.DAY_OF_MONTH);
			for (int i=1; i<=remainingDays; i++) {
				currentDate.add(Calendar.DATE, 1);
				lastWeek.put(currentDate.get(Calendar.DAY_OF_WEEK), new CalendarDayDto(currentDate));		
			}
			
			// For the next Month
			int nextMonthDays = Calendar.SATURDAY - currentDate.get(Calendar.DAY_OF_WEEK);
			logger.debug("nextMonthDays = " + nextMonthDays);
			for (int i=1; i<=nextMonthDays; i++) {
				currentDate.add(Calendar.DATE, 1);
				logger.debug("Put into last Week= " + sdf.format(currentDate.getTime()));
				lastWeek.put(currentDate.get(Calendar.DAY_OF_WEEK), new CalendarDayDto(currentDate));		
			}

			calendarList.add(lastWeek);
		}
		
		return calendarList;
	}
		
}
