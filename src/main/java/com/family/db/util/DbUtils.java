package com.family.db.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.family.exception.ServiceException;
import com.spaceprogram.simplejpa.util.AmazonSimpleDBUtil;

public class DbUtils {

	public static void main(String[] args) {
		String nextSequenceId = nextSequenceId();
		System.out.println("Next Sequence = " + nextSequenceId);

		System.out.println("long value = " + Long.parseLong(nextSequenceId));


		System.out.println("CurrentTimestamp = " + getCurrentTimestamp());
		
		BigDecimal a1 = new BigDecimal(
				"8273872368712658763457862348566489.7162578164578825032512");
		BigDecimal b1 = new BigDecimal(
				"8762347526136571645982560956723521.8374618726457432145631");
		BigDecimal c1 = a1.add(b1);
		System.out.println("ci =" + c1);
		System.out.println(" c1 = " + c1.toPlainString());
		System.out.println("printAmount: c1 =" + DbUtils.printAmount(c1));	
		
		
		double a2 = 1234.5678;

		double b2 = 1238.1273;
		
		double c2 = a2 + b2;
		System.out.println("c2 =" + c2);
		System.out.println("printAmount: c2 =" + DbUtils.printAmount(c2));
			
	}

	/**
	 * The purpose of this method is to generate id by using timeStamp in the
	 * format of yyHHddhhMMS
	 * 
	 * @return a string by printing out the timestamp in the format of
	 *         yyMMddhhmmssSSS
	 */
	public static synchronized final String nextSequenceId() {
		DateTime dt = new DateTime();
		DateTimeZone dtZone = DateTimeZone.forID("America/Los_Angeles");
		DateTime dtla = dt.withZone(dtZone);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyMMddHHmmssSSS");

		return dtla.toString(fmt);

	}

	public static final String getCurrentTimestamp() {		
		return AmazonSimpleDBUtil.encodeDate(Calendar.getInstance().getTime());
	}

	/**
	 * From java.util.Date to yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static final String encodeDate(Date date) {
		return AmazonSimpleDBUtil.encodeDate(date);
	}
	
	/**
	 * From java.util.Date to yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static final Date decodeDate(String dateString){
		Date date = null;
		try {
			date = AmazonSimpleDBUtil.decodeDate(dateString);
		} catch(Exception e) {
			throw new ServiceException("Fail to parse dateString: " + dateString);
		}
		return date;
	}


	/**
	 * From BigDecimal to xxxx.xx
	 * 
	 * @param dob
	 * @return
	 */
	public static final String printAmount(BigDecimal amount) {
		BigDecimal newAmount = amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return newAmount.toPlainString();
	}


	/**
	 * From BigDecimal to xxxx.xx
	 * 
	 * @param dob
	 * @return
	 */
	public static final String printAmount(double amount) {	
		return printAmount(new BigDecimal(String.valueOf(amount)));
	}


}
