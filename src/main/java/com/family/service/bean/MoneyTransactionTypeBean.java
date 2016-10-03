package com.family.service.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.db.domain.MoneyTransactionTypeDomain;
import com.family.enums.StandardMoneyTransactionEnum;

public class MoneyTransactionTypeBean {

	private long id;
	
	private String name;
	
	private String ioe;

	private Boolean active;
	
	private long familyId;
	
	public MoneyTransactionTypeBean() {}
	
	public MoneyTransactionTypeBean(StandardMoneyTransactionEnum smte) {
		this.id = smte.getId();
		this.name = smte.name();
		this.ioe = smte.getIoe().name();
		this.active = smte.isActive();
		this.familyId = smte.getFamilyId();		
	}

	
	public MoneyTransactionTypeBean(MoneyTransactionTypeDomain domain) {
		this.setId(Long.parseLong(domain.getId()));
		this.setFamilyId(Long.parseLong(domain.getFamilyId()));
		this.setActive(Boolean.parseBoolean(domain.getActive()));
		this.setIoe(domain.getIoe());
		this.setName(domain.getName());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIoe() {
		return ioe;
	}

	public void setIoe(String ioe) {
		this.ioe = ioe;
	}

	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}

	public long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(long familyId) {
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
