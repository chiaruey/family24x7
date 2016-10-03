package com.family.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.service.bean.MoneyTransactionBean;
import com.family.service.bean.MoneyTransactionTypeBean;

public class SingleTracTypeSummaryDto implements Serializable, Comparable<SingleTracTypeSummaryDto> {
	
	private static final long serialVersionUID = -1388441218881663201L;

	private MoneyTransactionTypeBean transactionType;
	
	private BigDecimal totalAmount;
		
	private BigDecimal pct;
	
	private int tracCount;
		
	private Set<MoneyTransactionBean> transactions;

	public SingleTracTypeSummaryDto(MoneyTransactionTypeBean transactionType) {
		this.transactionType = transactionType;
		totalAmount = new BigDecimal(0, new MathContext(2));
		pct = new BigDecimal(0, new MathContext(2));
		tracCount = 0;
		transactions = new HashSet<MoneyTransactionBean>();
	}
	
	public MoneyTransactionTypeBean getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(MoneyTransactionTypeBean transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPct() {
		return pct;
	}

	public void setPct(BigDecimal pct) {
		this.pct = pct;
	}
	
	public int getTracCount() {
		return tracCount;
	}

	public void setTracCount(int tracCount) {
		this.tracCount = tracCount;
	}
	
	public void add(MoneyTransactionBean mtb) {
		totalAmount = totalAmount.add(mtb.getAmount());
		tracCount += 1;
		transactions.add(mtb);
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

	@Override
	public int compareTo(SingleTracTypeSummaryDto that) {
		
		return this.getTransactionType().getName().compareTo(that.getTransactionType().getName());
	}
	

	
}
