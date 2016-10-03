package com.family.web.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ActivateDto extends StandardDto implements Serializable {
	
	private static final long serialVersionUID = 5069451258873504450L;
	private boolean activated;

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.setSuccess(true);
		this.activated = activated;
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
