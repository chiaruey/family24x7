package com.family.db.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Table(name="secure_question_answer")
@Entity
public class SecureQuestionAnswerDomain extends AbstractBaseDomain implements Serializable  {

	private static final long serialVersionUID = 4654296271414927222L;
	
	private String userId;
	
	private String secureQuestionId;
	
	private String secureQuestionAnswer;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
