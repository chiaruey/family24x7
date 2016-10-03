package com.family.web.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.family.service.bean.MoneyTransactionBean;

public class MoneyTransactionDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String amount;
	private String comments;
	private String date;
	private String ioe;
	private String name;
	private long transTypeId;
	private long transId;
	
	public MoneyTransactionDto(){}
	
	public MoneyTransactionDto(String amount, String date, String ioe, int transId,
			String name, String comments) {
		super();
		this.amount = amount;
		this.comments = comments;
		this.date = date;
		this.ioe = ioe;
		this.transTypeId = transId;
		this.name = name;
	}
	
	public MoneyTransactionDto(MoneyTransactionBean trac) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		if (trac.getAmount() != null) this.setAmount(trac.getAmount().toPlainString());
		
		this.setComments(trac.getComments());
		
		if (trac.getTracDate() != null) this.setDate(sdf.format(trac.getTracDate()));
		
		if (trac.getTracType() != null) {
			this.setType(trac.getTracType().getIoe());
			this.setName(trac.getTracType().getName());			
			this.setTransTypeId(trac.getTracType().getId());
		}
		
		this.setTransId(trac.getId());
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIoe() {
		return ioe;
	}

	public void setType(String ioe) {
		this.ioe = ioe;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTransTypeId() {
		return transTypeId;
	}

	public void setTransTypeId(long transTypeId) {
		this.transTypeId = transTypeId;
	}

	public long getTransId() {
		return transId;
	}

	public void setTransId(long transId) {
		this.transId = transId;
	}

	public void setIoe(String ioe) {
		this.ioe = ioe;
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
