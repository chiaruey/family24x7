package com.family.enums;

public enum SectionEnum {
	
	Home("static/img/home.png"), 
	Money("static/img/money.png"),
	Calendar("static/img/calendar_wall_icon.png"), 
	Admin("static/img/admin.png");
	
	private final String wallImagePath;
	
	SectionEnum(String wallImagePath) {
		this.wallImagePath = wallImagePath;		
	}

	public String getWallImagePath() {
		return wallImagePath;
	}
	
	
	

}
