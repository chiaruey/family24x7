package com.family.web.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.service.bean.EventBean;

public class EventDto extends StandardDto implements Serializable {

	private static final long serialVersionUID = 7508483875293484992L;
	
	private long id;
	private String startDateTime;
	private String startTime;
	private String endDateTime;
	private String endTime;
	private String title;
	private String description;
	private Boolean allDay;
	
	private SimpleDateFormat dateTimeSdf = new SimpleDateFormat("MM/dd/yy 'at' HH:mm");
	private SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat dateSdf = new SimpleDateFormat("MM/dd/yy" );
	
	public EventDto() {}
	
	public EventDto(EventBean event) {
		this.id = event.getId();
		this.title = event.getTitle();
		this.description = event.getDescription();
		this.allDay = event.isAllDay();
		this.startDateTime = allDay ? dateSdf.format(event.getStartTime()) : dateTimeSdf.format(event.getStartTime());
		this.startTime = timeSdf.format(event.getStartTime());
		this.endDateTime = allDay ? " ALL DATE EVENT " : dateTimeSdf.format(event.getEndTime()) ;
		this.endTime = timeSdf.format(event.getEndTime());
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public Boolean getAllDay() {
		return allDay;
	}
	
	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
