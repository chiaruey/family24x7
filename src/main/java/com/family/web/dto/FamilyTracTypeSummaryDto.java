package com.family.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * The transaction summary data which is used to populate
 * money content page.
 * 
 * @author Daddy
 *
 */
public class FamilyTracTypeSummaryDto implements Serializable {
	
	private static final long serialVersionUID = -1256851174853293749L;

	Map<Long, SingleTracTypeSummaryDto> tracTypeSummaryMap;
	
	private Date startTime;
	
	private Date endTime;
	
	private long familyId;

	public FamilyTracTypeSummaryDto(long familyId, Date startTime, Date endTime, Map<Long, SingleTracTypeSummaryDto> tracTypeSummaryMap) {
		this.familyId = familyId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.tracTypeSummaryMap = tracTypeSummaryMap;
	}
	
	public Map<Long, SingleTracTypeSummaryDto> getTracTypeSummaryMap() {
		return tracTypeSummaryMap;
	}

	public void setTracTypeSummaryMap(
			Map<Long, SingleTracTypeSummaryDto> tracTypeSummaryMap) {
		this.tracTypeSummaryMap = tracTypeSummaryMap;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
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
