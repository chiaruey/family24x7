package com.family.service;

import java.util.Date;
import java.util.List;

import com.family.enums.GenderEnum;
import com.family.service.bean.FamilyBean;
import com.family.service.bean.UserBean;
import com.family.web.controller.model.FamilyMemberModel;
import com.family.web.controller.model.MemberModel;

public interface AccountService {

	/*
	 * Family
	 */
	FamilyBean findFamilyByFamilyName(String familyName);
	
	FamilyBean findFamily(long familyId);
	
	List<UserBean> findFamilyMemberList(long familyId);
	
	
	/*
	 * HouseHead 
	 */
	void addHousehead(String familyName, String username, String email, String firstName, String lastName, String password, Date dob, 
			String gender, String roleName, String street, String city, String state, String country, String zip, String secureQuestionId, String secureQuestionAnswer);
	
	void addHouseHead(MemberModel newMemberInfo);
	
	boolean isHouseHead(long familyId, long userid);


	/*
	 * FamilyMember 
	 */
	void addFamilyMember(UserBean user, FamilyMemberModel newFamilyMemberInfo);

	void addFamilyMember(UserBean user, String username, String email, String firstName, 
			String lastName, String password, String gender, String dobYearStr,
			String dobMonthStr, String dobDayStr, String role, boolean admin);

	void deleteFamilyMember(String username, UserBean user);

	void activateFamilyMember(String username, UserBean user, boolean activate);

	
	/*
	 * Update User
	 */
	void updateUser(UserBean user, String email, String firstName, String lastName, long secureQuestionId, String secureQuestionAnswer);
	
	void updateUser(UserBean user, String email, String firstName, String lastName, String password, long secureQuestionId, String secureQuestionAnswer);

	void updateUser(UserBean user, String email, String firstName, String lastName, String password, GenderEnum gender, String dobYearStr, 
			String dobMonthStr, String dobDayStr, String roleName, boolean admin);
	
	UserBean findUserByUsername(String username);
	
	UserBean findUserByUserid(long userId);
	
	void resetFamilyPassword(UserBean user, long familyUserId,  String familyUserName, String password);
}
