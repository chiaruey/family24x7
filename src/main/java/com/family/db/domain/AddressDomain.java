package com.family.db.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * SimpleDB entity
 * 
 * @author Daddy
 *
 */
@Entity
@Table(name="address")
public class AddressDomain  extends AbstractBaseDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String street;
	
	private String city;
	
	private String state;
	
	private String zip;
		
	private String country;

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
