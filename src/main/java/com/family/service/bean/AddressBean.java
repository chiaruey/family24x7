package com.family.service.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.db.domain.AddressDomain;
import com.family.db.util.DbUtils;

public class AddressBean implements Serializable {

	private static final long serialVersionUID = 4460871005131448862L;
	
	private long id;
	
	private String street;
	
	private String city;
	
	private String state;
	
	private String zip;
		
	private String country;

	private Date lastUpdateTime;

	public AddressBean() {}
	
	public AddressBean(AddressDomain domain) {
		if (domain != null) {
			this.setCity(domain.getCity());
			this.setCountry(domain.getCountry());
			this.setId(Long.parseLong(domain.getId()));
			this.setLastUpdateTime(DbUtils.decodeDate(domain.getUpdated()));
			this.setState(domain.getState());
			this.setStreet(domain.getStreet());
			this.setZip(domain.getZip());			
		}
		
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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
