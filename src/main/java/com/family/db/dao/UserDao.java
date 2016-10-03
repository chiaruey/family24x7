package com.family.db.dao;

import java.util.Date;
import java.util.List;

import com.family.db.domain.UserDomain;
import com.family.enums.GenderEnum;

public interface UserDao {

/*	UserDomain addUser(String username, String password, String firstName,
			String lastName, String email, Date dob,
			boolean admin, GenderEnum gender, boolean active, String roleName);*/

	////////
	// User
	
	/**
	 * find a user in SimpleDB
	 */
	UserDomain findUser(String id);
	
	/**
	 * find a user in SimpleDB
	 */
	UserDomain findUserByUsername(String username);
	
	List<UserDomain> findFamilyMemberList(String familyId);

	/**
	 * Update a user in SimpleDB
	 */
	UserDomain updateUser(String id, String  firstName, String  lastName, String  email, String  secureQuestionId, String  secureQuestionAnswer);
	
	UserDomain updateUser(String id, String  firstName, String  lastName, String password, String  email, String  secureQuestionId, String  secureQuestionAnswer);
	
	UserDomain updateUser(String id, String password, String firstName,
			String lastName, String email, Date dob,
			boolean admin, GenderEnum gender, boolean active, String roleName);
	
	UserDomain updateUser(String id, String password, String firstName,
			String lastName, String email, Date dob,
			boolean admin, GenderEnum gender, String roleName);
	
	public UserDomain resetFamilyPassword(String familyUserId, String password);
	
	void activateUser(String username, boolean activate);
	
	void deleteUser(String username);
	
	/////////////////
	// Family Member
	
	UserDomain addHousehead(String familyName, String username, String email, String firstName, String lastName, String password, Date dob, 
			String gender, String roleName, String street, String city, String state, String country, String zip, String  secureQuestionId, String  secureQuestionAnswer);

	void addFamilyMember(String username, String email, String firstName, String lastName, String password, Date dob, 
			String gender, String roleName, String familyId, String adminStr);

}
