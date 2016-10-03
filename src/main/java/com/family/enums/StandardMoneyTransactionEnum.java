package com.family.enums;

import static com.family.util.FamilyConstants.SYSTEM_FAMILY_ID;;

public enum StandardMoneyTransactionEnum {
	General_Expense(1L, IoeEnum.E, SYSTEM_FAMILY_ID, Boolean.TRUE), 
	Grocery(2L, IoeEnum.E, SYSTEM_FAMILY_ID, Boolean.TRUE), 
	Travel(3L, IoeEnum.E, SYSTEM_FAMILY_ID, Boolean.TRUE),
	Restaurant(4L, IoeEnum.E, SYSTEM_FAMILY_ID, Boolean.TRUE), 
	Bakery(5L, IoeEnum.E, SYSTEM_FAMILY_ID, Boolean.TRUE), 
	Gas(6L, IoeEnum.E, SYSTEM_FAMILY_ID, Boolean.TRUE), 
	Charity(7L, IoeEnum.E, SYSTEM_FAMILY_ID, Boolean.TRUE),
	General_Income(8L, IoeEnum.I, SYSTEM_FAMILY_ID, Boolean.TRUE), 
	Salary(9L, IoeEnum.I, SYSTEM_FAMILY_ID, Boolean.TRUE),
	Investment(10L, IoeEnum.I, SYSTEM_FAMILY_ID, Boolean.TRUE);
	
	private final long id;
	private final IoeEnum ioe;
	private final long familyId;
	private final boolean active;
	
	StandardMoneyTransactionEnum(long id, IoeEnum ioe, long familyId, boolean active) {
		this.id = id;
		this.ioe = ioe;
		this.familyId = familyId;
		this.active = active;
	}
	
	public long getId() {
		return id;
	}

	public IoeEnum getIoe() {
		return ioe;
	}

	public long getFamilyId() {
		return familyId;
	}

	public boolean isActive() {
		return active;
	}
	
}
