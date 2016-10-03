package com.family.db.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Table(name="money_transaction_type")
@Entity
public class MoneyTransactionTypeDomain extends AbstractBaseDomain implements Serializable  {

	private static final long serialVersionUID = 4654296271414927222L;
	
	private String name;
	
	private String ioe;

	private String active;
	
	private String familyId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIoe() {
		return ioe;
	}

	public void setIoe(String ioe) {
		this.ioe = ioe;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
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
