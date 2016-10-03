package com.family.web.dto;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.family.service.bean.UserBean;

public class UsernameValdationDto extends StandardDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String email;

	private String username;
//	
//	private long secureQuestionId;


	public UsernameValdationDto() {
		super(false);
	}
	
	public UsernameValdationDto(UserBean user) {
		super(true);
		this.setId(user.getId());
		this.setEmail(user.getEmail());
		this.setUsername(user.getUsername());
//		this.setSecureQuestionId(user.getSecureQuestionId());
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

//
//	public long getSecureQuestionId() {
//		return secureQuestionId;
//	}
//
//	public void setSecureQuestionId(long secureQuestionId) {
//		this.secureQuestionId = secureQuestionId;
//	}
//

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
