package com.family.db.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "family")
public class FamilyDomain  extends AbstractBaseDomain implements Serializable {
	
	private static final long serialVersionUID = -7719089365811879895L;

	private String househeadId;
	
	private String addressId;
	
	private String familyname;

	public String getFamilyname() {
		return familyname;
	}

	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

	public String getHouseheadId() {
		return househeadId;
	}

	public void setHouseheadId(String househeadId) {
		this.househeadId = househeadId;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
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
