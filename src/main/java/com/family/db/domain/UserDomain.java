package com.family.db.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "user")
public class UserDomain extends AbstractBaseDomain implements Serializable {

	private static final long serialVersionUID = 5152366428759072449L;

	private String email;
	
	private String password;

	private String username;
	
	private String admin;
	
	private String dob;
	
	private String firstName;
	
	private String lastName;
	
	private String gender;
	
	private String active;

	private String familyId;
	
	private String roleName;
	
	private String secureQuestionId;
	
	private String secureQuestionAnswer;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}


	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSecureQuestionId() {
		return secureQuestionId;
	}

	public void setSecureQuestionId(String secureQuestionId) {
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
