package com.family.service.bean;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class FamilyBean implements Serializable {

	private static final long serialVersionUID = 355195774031785615L;

	private long id;

	private UserBean househead;
	
	private AddressBean address;
	
	private String familyName;
	
	private List<UserBean> familyMember;

	public FamilyBean(){}
	
	public FamilyBean(long familyId, String familyName){
		this.familyName = familyName;
		this.id = familyId;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public UserBean getHousehead() {
		return househead;
	}

	public void setHousehead(UserBean househead) {
		this.househead = househead;
	}

	public AddressBean getAddress() {
		return address;
	}

	public void setAddress(AddressBean address) {
		this.address = address;
	}
	
	public List<UserBean> getFamilyMember() {
		return familyMember;
	}

	public void setFamilyMember(List<UserBean> familyMember) {
		this.familyMember = familyMember;
	}
	
	
	public void addNewFamilyMember(UserBean user) {
		this.familyMember.add(user);
	}
	
	public void deleteFamilyMember(UserBean user) {
		this.familyMember.remove(user);
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
