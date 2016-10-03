package com.family.enums;

public enum SecureQuestionEnum {
	NO_SECURE_QUESTION(0L, "No secure question"),
	FATHER_BORN_CITY(1L, "In what city was your father born?"),
	FATHER_BORN_YEAR(2L, "In what year was your father born?"),
	FAVORATE_COLOR(3L, "What is your favorate color?"),
	FAVORATE_MOVIE(4L, "What is your favorate movie?"),
	FAVORATE_TEAM(5L, "What is your favorite team?"),
	FIRST_CAR_MODEL(6L, "What is the model of your first car?"),
	FIRST_JOB_COMPANY(7L, "What was the company for your first job?"),
	FIRST_JOB_TOWN(8L, "In what town was your first job?"),
	MOTHER_BORN_CITY(9L, "In what city was your mother born?"),
	MOTHER_BORN_YEAR(10L, "In what year was your mother born?"),
	SCHOOL_SIXTH_GRADE(11L, "What school did you attend for sixth grade?");

	private final long id;
	private final String secureQuestionText;
	
	SecureQuestionEnum(long id, String secureQuestionText) {
		this.id = id;
		this.secureQuestionText = secureQuestionText;
	}

	public long getId() {
		return id;
	}

	public String getSecureQuestionText() {
		return secureQuestionText;
	}

}
