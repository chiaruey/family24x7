package com.family.web.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.enums.SectionEnum;
import com.family.service.bean.WallMessageBean;

public class WallMessageDto extends StandardDto implements Serializable,  Comparable<WallMessageDto> { 

	private static final long serialVersionUID = -5048635095164385225L;
	
	private long id;

	private Date createDate;
	
	private String message;
	
	private String imagePath;

	public WallMessageDto() {
		
	}
	
	public WallMessageDto(WallMessageBean wallMessage) {
		this.id = wallMessage.getId();
		this.createDate = wallMessage.getCreateDate();
		this.message = wallMessage.getMessage();
		this.imagePath = SectionEnum.valueOf(StringUtils.trim(wallMessage.getSection())).getWallImagePath();	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public int compareTo(WallMessageDto o) {
		return this.getCreateDate().before(o.createDate) ? 1:-1;
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
