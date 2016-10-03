package com.family.service.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.db.domain.EventDomain;
import com.family.db.util.DbUtils;

@SuppressWarnings("serial")
public class EventBean implements Serializable, Comparable<EventBean>{
	
	private long id;
	private FamilyBean family;
	
	private Date startTime;
	
	private Date endTime;
	
	private String title;
	
	private String description;
	
	private boolean allDay;
	
	public EventBean() {}
	
	public EventBean(EventDomain eventDomain) {	
		this.setId(Long.parseLong(eventDomain.getId()));
		this.setAllDay(Boolean.parseBoolean(eventDomain.getAllDay()));
		this.setDescription(eventDomain.getDescription());
		this.setEndTime(DbUtils.decodeDate(eventDomain.getEndTime()));
		this.setStartTime(DbUtils.decodeDate(eventDomain.getStartTime()));
		this.setTitle(eventDomain.getTitle());
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public FamilyBean getFamily() {
		return family;
	}

	public void setFamily(FamilyBean family) {
		this.family = family;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

    public int compareTo(EventBean that) {
    	
    	if (this.getStartTime() == null) {
    		return -1;
    	} else if (that == null || that.getStartTime() == null) {
    		return 1;
    	} else {
    		return this.getStartTime().before(that.getStartTime()) ? -1 : 1;
    	}
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
