package com.family.web.util;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.family.service.bean.UserBean;
import com.family.web.controller.model.MemberModel;

public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String USER_SESSION = "UserSession";

	private List<UserBean> familyMembers;
	
	private MemberModel houseHeadInfo;
	
	public List<UserBean> getFamilyMembers() {
		return familyMembers;
	}

	public void setFamilyMembers(List<UserBean> familyMembers) {
		this.familyMembers = familyMembers;
	}

	public MemberModel getHouseHeadInfo() {
		return houseHeadInfo;
	}

	public void setHouseHeadInfo(MemberModel houseHeadInfo) {
		this.houseHeadInfo = houseHeadInfo;
	}

	/**
	 * get UserSession from HttpSession
	 */
	public static UserSession getUserSession(HttpSession session) {
		UserSession userSession = (UserSession) session.getAttribute(USER_SESSION);
		if (userSession == null) {
			return new UserSession();
		} else {
			return userSession;
		}	
	}
	
	/**
	 * Store UserSession in HttpSession
	 */
	public static void storeUserSession(HttpSession session, HttpSession userSession) {
		session.setAttribute(USER_SESSION, userSession);
	}
	
	public static void storeHouseheadInfo(HttpSession session, MemberModel houseHeadInfo) {
		UserSession userSession = (UserSession) session.getAttribute(USER_SESSION);
		if (userSession == null) {
			userSession = new UserSession();
		} 
		
		userSession.setHouseHeadInfo(houseHeadInfo);
		session.setAttribute(USER_SESSION, userSession);
	}
	
	public static MemberModel retrieveHouseheadInfo(HttpSession session) {
		UserSession userSession = (UserSession) session.getAttribute(USER_SESSION);
		MemberModel houseHead = null;
		
		if (userSession != null) {
			houseHead = userSession.getHouseHeadInfo();
		} 
		
		return houseHead;
	}
	
}
