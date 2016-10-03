package com.family.enums;

public enum RoleEnum {
	Single(true), Husband(true), Wife(true), Dad(true), Mom(true), Son(false), Daughter(false) ;
	
	private boolean admin;

	private RoleEnum(boolean admin) {
		this.admin = admin;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
}
