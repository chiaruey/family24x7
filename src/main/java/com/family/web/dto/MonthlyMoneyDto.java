package com.family.web.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.service.bean.MoneyTransactionBean;
import com.family.service.bean.MoneyTransactionTypeBean;


public class MonthlyMoneyDto  implements Serializable {

	private static final long serialVersionUID = -2548379758150784917L;
	private String year;
	private String month;
	private String monthName;
	private String monthAbbr;
	
	private String prevMonth;
	private String prevYear;
	
	private String nextMonth;
	private String nextYear;
	
	List<MoneyTransactionBean> transitions;
	
	List<MoneyTransactionTypeBean> transitionTypes;
	
	Collection<SingleTracTypeSummaryDto> expenseTrancSummary;
	
	Collection<SingleTracTypeSummaryDto> incomeTrancSummary;
	
	public MonthlyMoneyDto(Calendar calendar) 
	{
		SimpleDateFormat monthNameSdf = new SimpleDateFormat("MMMMM", Locale.US);
		SimpleDateFormat monthAbbrSdf = new SimpleDateFormat("MMM");
		this.year = String.valueOf(calendar.get(Calendar.YEAR));
		this.month = String.valueOf(calendar.get(Calendar.MONTH));
		this.monthName = monthNameSdf.format(calendar.getTime());
		this.monthAbbr = monthAbbrSdf.format(calendar.getTime());
		
		// Previous month
		calendar.add(Calendar.MONTH, -1);
		this.prevMonth = String.valueOf(calendar.get(Calendar.MONTH));
		prevYear = String.valueOf(calendar.get(Calendar.YEAR));
		
		// Next month
		calendar.add(Calendar.MONTH, 2);
		this.nextMonth = String.valueOf(calendar.get(Calendar.MONTH));
		nextYear = String.valueOf(calendar.get(Calendar.YEAR));	
	}
	
	public MonthlyMoneyDto(int year, int month) 
	{
		this(new GregorianCalendar(year, month, 1));
	}


	public String getYear() {
		return year;
	}

	public String getMonth() {
		return month;
	}

	public String getMonthName() {
		return monthName;
	}

	public String getMonthAbbr() {
		return monthAbbr;
	}

	public String getPrevMonth() {
		return prevMonth;
	}

	public String getPrevYear() {
		return prevYear;
	}

	public String getNextMonth() {
		return nextMonth;
	}

	public String getNextYear() {
		return nextYear;
	}

	public List<MoneyTransactionBean> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<MoneyTransactionBean> transitions) {
		this.transitions = transitions;
	}

	public List<MoneyTransactionTypeBean> getTransitionTypes() {
		return transitionTypes;
	}

	public void setTransitionTypes(List<MoneyTransactionTypeBean> transitionTypes) {
		this.transitionTypes = transitionTypes;
	}

	public Collection<SingleTracTypeSummaryDto> getExpenseTrancSummary() {
		return expenseTrancSummary;
	}

	public void setExpenseTrancSummary(
			Collection<SingleTracTypeSummaryDto> expenseTrancSummary) {
		this.expenseTrancSummary = expenseTrancSummary;
	}

	public Collection<SingleTracTypeSummaryDto> getIncomeTrancSummary() {
		return incomeTrancSummary;
	}

	public void setIncomeTrancSummary(
			Collection<SingleTracTypeSummaryDto> incomeTrancSummary) {
		this.incomeTrancSummary = incomeTrancSummary;
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
