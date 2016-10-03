package com.family.service.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.db.domain.MoneyTransactionDomain;
import com.family.db.util.DbUtils;

public class MoneyTransactionBean {

	private long id;
	
	private FamilyBean family;

	private MoneyTransactionTypeBean tracType;
	
	private String comments;
		
	private BigDecimal amount;
	
	private Date tracDate;

	public MoneyTransactionBean() {
		
	}
	
	public MoneyTransactionBean(MoneyTransactionDomain mtd) {
		this.setId(Long.parseLong(mtd.getId()));
		this.setAmount(new BigDecimal(mtd.getAmount()));
		this.setComments(mtd.getComments());
		this.setTracDate(DbUtils.decodeDate(mtd.getTransactionDate()));
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

	public MoneyTransactionTypeBean getTracType() {
		return tracType;
	}

	public void setTracType(MoneyTransactionTypeBean tracType) {
		this.tracType = tracType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getTracDate() {
		return tracDate;
	}

	public void setTracDate(Date traccDate) {
		this.tracDate = traccDate;
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
