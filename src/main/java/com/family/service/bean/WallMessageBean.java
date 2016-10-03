package com.family.service.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.db.domain.WallMessageDomain;
import com.family.db.util.DbUtils;

/**
 * This is the layer introduced for translate Simpledb's string to real type
 * 
 * @author Daddy
 */
public class WallMessageBean implements Serializable {
	
	private static final long serialVersionUID = -204761605992716782L;

	private long id;
	
	private Date createDate;
	
	private String section;
	
	private String message;
	
	private long userId;
	
	private long familyId;

	public WallMessageBean(){}
	
	public WallMessageBean(WallMessageDomain wmDomain){
		this.setId(Long.valueOf(wmDomain.getId()));
		this.setCreateDate(DbUtils.decodeDate(wmDomain.getCreated()));
		this.setFamilyId(Long.valueOf(wmDomain.getFamilyId()));
		this.setMessage(wmDomain.getMessage());
		this.setSection(wmDomain.getSection());
		this.setUserId(Long.valueOf(wmDomain.getUserId()));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setFamilyId(long familyId) {
		this.familyId = familyId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	

	public long getUserId() {
		return userId;
	}

	public long getFamilyId() {
		return familyId;
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
