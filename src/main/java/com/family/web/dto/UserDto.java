package com.family.web.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.service.bean.FamilyBean;
import com.family.service.bean.UserBean;
import com.family.web.util.MyCalendarUtils;

public class UserDto extends StandardDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String email;

	private String password;

	private String username;

	private String admin;

	private Date dob;
	
	private int dobMonth;
	
	private int dobDay;
	
	private int dobYear;

	private String firstName;

	private String lastName;

	private String gender;

	private String roleName;
	
	private long secureQuestionId;
	
	private String secureQuestionAnswer;
	
	private FamilyBean family;
	
	private boolean isHoh;

	public UserDto() {
		super(false);
	}
	
	public UserDto(UserBean user) {
		super(true);
		this.setId(user.getId());
		this.setEmail(user.getEmail());
		this.setPassword(user.getPassword());
		this.setUsername(user.getUsername());
		this.setAdmin(String.valueOf(user.isAdmin()));
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setGender(user.getGender());
		this.setRoleName(user.getRoleName());
		
		// Date of birth
		Calendar dobCalendar = Calendar.getInstance();
		dobCalendar.setTime(user.getDob());
		this.setDobMonth(dobCalendar.get(Calendar.MONTH));
		this.setDobDay(dobCalendar.get(Calendar.DAY_OF_MONTH));
		this.setDobYear(dobCalendar.get(Calendar.YEAR));
		
		this.setSecureQuestionId(user.getSecureQuestionId());
		this.setSecureQuestionAnswer(user.getSecureQuestionAnswer());
	}

	public boolean isHoh() {
		return isHoh;
	}

	public void setHoh(boolean isHoh) {
		this.isHoh = isHoh;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public FamilyBean getFamily() {
		return family;
	}

	public void setFamily(FamilyBean family) {
		this.family = family;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return (dob != null) ? MyCalendarUtils.getAge(dob) : 0;
	}

	public int getDobMonth() {
		return dobMonth;
	}

	public void setDobMonth(int dobMonth) {
		this.dobMonth = dobMonth;
	}

	public int getDobDay() {
		return dobDay;
	}

	public void setDobDay(int dobDay) {
		this.dobDay = dobDay;
	}

	public int getDobYear() {
		return dobYear;
	}

	public void setDobYear(int dobYear) {
		this.dobYear = dobYear;
	}	

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public long getSecureQuestionId() {
		return secureQuestionId;
	}

	public void setSecureQuestionId(long secureQuestionId) {
		this.secureQuestionId = secureQuestionId;
	}

	public String getSecureQuestionAnswer() {
		return secureQuestionAnswer;
	}

	public void setSecureQuestionAnswer(String secureQuestionAnswer) {
		this.secureQuestionAnswer = secureQuestionAnswer;
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
