package com.family.web.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class VerificationDto  extends StandardDto implements Serializable  {
	
	private static final long serialVersionUID = -1844010437583783929L;

	private boolean result;

	public VerificationDto() {
		this(false, false);
	}
	
	public VerificationDto(boolean success, boolean result) {
		this.setSuccess(success);
		this.setResult(result);
	}
	
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
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
