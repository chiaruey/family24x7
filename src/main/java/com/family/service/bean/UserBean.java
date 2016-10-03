package com.family.service.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.db.domain.UserDomain;
import com.family.db.util.DbUtils;
import com.family.util.MyUtils;

public class UserBean implements Serializable {

	private static final long serialVersionUID = -4710337145378203034L;

	private long id;
		
	private String email;
	
	private String password;

	private String username;
	
	private boolean admin;
	
	private Date dob;
	
	private String firstName;

	private String lastName;
	
	private String gender;
	
	private boolean active;
	
	private Date lastUpdateTime;

	private long familyId;
	
	private String roleName;
	
	private long secureQuestionId;
	
	private String secureQuestionAnswer;


	public UserBean() {}
	
	public UserBean(UserDomain userDomain) {
		if (userDomain != null) {
			this.setAdmin(MyUtils.isTrue(userDomain.getAdmin()));
			this.setDob(DbUtils.decodeDate(userDomain.getDob()));
			this.setEmail(userDomain.getEmail());
			this.setFamilyId(Long.parseLong(userDomain.getFamilyId()));
			this.setFirstName(userDomain.getFirstName());
			this.setGender(userDomain.getGender());
			this.setId(Long.parseLong(userDomain.getId()));
			this.setLastName(userDomain.getLastName());
			this.setPassword(userDomain.getPassword());
			this.setUsername(userDomain.getUsername());
			this.setRoleName(userDomain.getRoleName());
			this.setActive(MyUtils.isTrue(userDomain.getActive()));
			this.setLastUpdateTime(DbUtils.decodeDate(userDomain.getUpdated()));
			if (StringUtils.isNotBlank(userDomain.getSecureQuestionId())) {
				this.setSecureQuestionId(Long.parseLong(userDomain.getSecureQuestionId()));;
			}	
			this.setSecureQuestionAnswer(userDomain.getSecureQuestionAnswer());
		}
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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


	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(long familyId) {
		this.familyId = familyId;
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
