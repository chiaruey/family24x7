package com.family.util;

import org.apache.commons.lang.StringUtils;

import com.family.enums.SecureQuestionEnum;

public class MyUtils {
	
	public static boolean isTrue(String s) {
		return "True".equalsIgnoreCase(s)||"t".equalsIgnoreCase(s)||"y".equalsIgnoreCase(s);
	}
	
	public static String upperCase(String str) {
		return StringUtils.upperCase(StringUtils.trimToEmpty(str));
	}
	
	public static String lowerCase(String str) {
		return StringUtils.lowerCase(StringUtils.trimToEmpty(str));
	}

	public static SecureQuestionEnum findSecureQuestionEnumById(long id) {
		
		for (SecureQuestionEnum sqe: SecureQuestionEnum.values()) {
			if (sqe.getId() == id) {
				return sqe;
			}
		}
		return SecureQuestionEnum.NO_SECURE_QUESTION;
	}

}
