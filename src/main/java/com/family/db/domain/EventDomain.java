package com.family.db.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.db.util.DbUtils;


@Entity
@Table(name = "event")
public class EventDomain extends AbstractBaseDomain implements Serializable, Comparable<EventDomain> {

	private static final long serialVersionUID = -6915842969868139166L;

	private String familyId;
	
	private String startTime;
	
	private String endTime;
	
	private String title;
	
	private String description;
	
	private String allDay;
	
	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
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

	public String getAllDay() {
		return allDay;
	}

	public void setAllDay(String allDay) {
		this.allDay = allDay;
	}
	
	
    public int compareTo(EventDomain that) {
    	
    	if (this.getStartTime() == null) {
    		return -1;
    	} else if (that == null || that.getStartTime() == null) {
    		return 1;
    	} else {
    		Date thisStartTime = DbUtils.decodeDate(this.getStartTime()) ;
    		Date thatStartTime = DbUtils.decodeDate(that.getStartTime());
    		return thisStartTime.before(thatStartTime) ? -1 : 1;
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
