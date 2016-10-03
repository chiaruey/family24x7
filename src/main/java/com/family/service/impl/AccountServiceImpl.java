package com.family.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.family.db.dao.AddressDao;
import com.family.db.dao.FamilyDao;
import com.family.db.dao.UserDao;
import com.family.db.domain.FamilyDomain;
import com.family.db.domain.UserDomain;
import com.family.enums.GenderEnum;
import com.family.enums.RoleEnum;
import com.family.enums.SectionEnum;
import com.family.exception.AlreadyRegisteredException;
import com.family.exception.ServiceException;
import com.family.service.AccountService;
import com.family.service.WallService;
import com.family.service.bean.AddressBean;
import com.family.service.bean.FamilyBean;
import com.family.service.bean.UserBean;
import com.family.web.controller.model.FamilyMemberModel;
import com.family.web.controller.model.MemberModel;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FamilyDao familyDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	WallService wallService;
	
	private Logger  logger = Logger.getLogger("com.family.service");

	///////////////
	// Family

	@Transactional
	public FamilyBean findFamily(long familyId) {
		FamilyDomain familyDomain = familyDao.findFamilyById(String.valueOf(familyId));
		FamilyBean fb = new FamilyBean(familyId, familyDomain.getFamilyname());
		fb.setAddress(new AddressBean(addressDao.findAddress(familyDomain.getAddressId())));
		fb.setHousehead(this.findUserByUserid(Long.parseLong(familyDomain.getHouseheadId())));
		fb.setId(Long.parseLong(familyDomain.getId()));
		fb.setFamilyMember(this.findFamilyMemberList(Long.parseLong(familyDomain.getId())));

		return fb;
	}

	public List<UserBean> findFamilyMemberList(long familyId) {
		List<UserBean> familyMemberList = new ArrayList<UserBean>();
		List<UserDomain> userDomainList = userDao.findFamilyMemberList(String.valueOf(familyId));
		
		for (UserDomain userDomain : userDomainList) {
			UserBean userBean = new UserBean(userDomain);
			familyMemberList.add(userBean);
		}
		return familyMemberList;
	}


	public FamilyBean findFamilyByFamilyName(String familyName) {
		
		FamilyBean familyBean = null;
		FamilyDomain familyDomain = familyDao.findFamilyByFamilyname(familyName);
		if (familyDomain != null && StringUtils.isNotBlank(familyDomain.getId())) {
			familyBean = new FamilyBean(Long.parseLong(familyDomain.getId()), familyDomain.getFamilyname());
			familyBean.setAddress(new AddressBean(addressDao.findAddress(familyDomain.getAddressId())));
			familyBean.setHousehead(this.findUserByUserid(Long.parseLong(familyDomain.getHouseheadId())));				
		}
	
		return familyBean;
	}

	
	///////////////
	// House Head

	@Transactional
	public void addHouseHead(MemberModel newMemberInfo) {
		
		if (findUserByUsername(newMemberInfo.getUsername()) != null) {
			throw new AlreadyRegisteredException("This username " + newMemberInfo.getUsername() + " has already been registered in the system!");
		}
		
		try {
			RoleEnum role = RoleEnum.valueOf(newMemberInfo.getRole());
			Date dob = new Date(new GregorianCalendar(Integer.parseInt(newMemberInfo.getDobYear()), Integer.parseInt(newMemberInfo.getDobMonth()),
					Integer.parseInt(newMemberInfo.getDobDay())).getTime().getTime());
			
			UserDomain userDomain = userDao.addHousehead(newMemberInfo.getFamilyName(), newMemberInfo.getUsername(), newMemberInfo.getEmail(), newMemberInfo.getFirstName(),  newMemberInfo.getLastName(),
					newMemberInfo.getPassword(), dob, newMemberInfo.getGender(), role.name(), newMemberInfo.getStreet(), 
					newMemberInfo.getCity(), newMemberInfo.getState(), newMemberInfo.getCountry(), newMemberInfo.getZip(),
					newMemberInfo.getSecureQuestion(), newMemberInfo.getSecureQuestionAnswer());
			
			String message =  newMemberInfo.getUsername() + " has created the family: " + newMemberInfo.getFamilyName();	
			
			wallService.addWallMessage(Long.parseLong(userDomain.getId()), Long.parseLong(userDomain.getFamilyId()), SectionEnum.Admin, message);
	
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}

	}

	@Transactional
	@Override
	public void addHousehead(String familyName, String username,
			String email, String firstName, String lastName,
			String password, Date dob, String gender,
			String roleName, String street, String city, String state,
			String country, String zip, String secureQuestionId, String secureQuestionAnswer) {
		
		if (findUserByUsername(username) != null) {
			throw new AlreadyRegisteredException("This username " + username + " has already been registered in the system!");
		}
		
		try {
			userDao.addHousehead(familyName, username, email, firstName, lastName, password, dob, gender, roleName, street, 
					city, state, country , zip, secureQuestionId, secureQuestionAnswer);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}

	}

	@Transactional
	public boolean isHouseHead(long familyId, long userid) {
		FamilyDomain familyDomain = familyDao.findFamilyById(String.valueOf(familyId));
		
		boolean result = (familyDomain != null) && StringUtils.equals(String.valueOf(userid), familyDomain.getHouseheadId());
		
		 
		return result;
	}

	//////////////////
	// Family Member
	
	@Transactional
	public void addFamilyMember(UserBean user, FamilyMemberModel newFamilyMemberInfo) {
		
	 addFamilyMember(user, newFamilyMemberInfo.getUsername(), newFamilyMemberInfo.getEmail(), newFamilyMemberInfo.getFirstName(),  newFamilyMemberInfo.getLastName(), newFamilyMemberInfo.getPassword(), 
				newFamilyMemberInfo.getGender(), newFamilyMemberInfo.getDobYear(), newFamilyMemberInfo.getDobMonth(), newFamilyMemberInfo.getDobDay(), 
				newFamilyMemberInfo.getRole(), Boolean.parseBoolean(newFamilyMemberInfo.getAdmin()));
		
	}

	@Transactional
	public void addFamilyMember(UserBean loggedInUser, String username, String email, String firstName, String lastName, String password, String gender, String dobYearStr, 
			String dobMonthStr, String dobDayStr, String role, boolean admin) {
		
		if (findUserByUsername(username) != null) {
			throw new AlreadyRegisteredException("This username " + username + " has already been registered in the system!");
		}
		
		try {
			Date dob = new Date(new GregorianCalendar(Integer.parseInt(dobYearStr), Integer.parseInt(dobMonthStr),
					Integer.parseInt(dobDayStr)).getTime().getTime());
			
			logger.debug("loggedInUser = " + loggedInUser);
			
			userDao.addFamilyMember(username, email, firstName, lastName, password, dob, gender, role, String.valueOf(loggedInUser.getFamilyId()), String.valueOf(admin));
			
			String message = loggedInUser.getFirstName() + " has added family member " + username ;	
			wallService.addWallMessage(loggedInUser, SectionEnum.Admin, message);		
		} catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException("addFamilyMember> " + e.getMessage() + " : " + ExceptionUtils.getStackTrace(e));
		}
	}
	

	
	@Transactional
	public void deleteFamilyMember(String username, UserBean user) {
	
		userDao.deleteUser(username);
		
		String message = user.getFirstName() + " has deleted family member " + username ;	
		wallService.addWallMessage(user, SectionEnum.Admin, message);		
	}
	
	

	@Transactional
	public void activateFamilyMember(String username, UserBean user, boolean activate) {
			
		userDao.activateUser(username, activate);
		String activateText = activate ? "activated" : "deactivated";
		String message = user.getFirstName() + " has " + activateText + " family member : " + username ;
		wallService.addWallMessage(user,  SectionEnum.Admin, message);
	}


	/////////////
	// User

	@Transactional
	public void updateUser(UserBean user, String email, String firstName, String lastName, long secureQuestionId, String secureQuestionAnswer) {

		userDao.updateUser( String.valueOf(user.getId()), firstName, lastName,  email,  String.valueOf(secureQuestionId), secureQuestionAnswer);
		
		String message = "This user " + user.getUsername() + " has been updated" ;
		
		wallService.addWallMessage(user, SectionEnum.Admin, message);	
	}
	
	@Transactional
	public void updateUser(UserBean user, String email, String firstName, String lastName, String password, long secureQuestionId, String secureQuestionAnswer) {

		userDao.updateUser( String.valueOf(user.getId()), firstName, lastName, password, email,  String.valueOf(secureQuestionId), secureQuestionAnswer);
		
		String message = "This user " + user.getUsername() + " has been updated" ;
		
		wallService.addWallMessage(user, SectionEnum.Admin, message);	
	}
	
	
	@Transactional
	public void resetFamilyPassword(UserBean user, long familyUserId,  String familyUserName, String password) {
		
		userDao.resetFamilyPassword( String.valueOf(familyUserId),  password);
		
		String message = user.getUsername() + " has reset the password for " + familyUserName ;
		
		wallService.addWallMessage(user, SectionEnum.Admin, message);	
		
	}
	
	@Transactional
	public void updateUser(UserBean user, String email, String firstName, String lastName, String password, GenderEnum gender, String dobYearStr, 
			String dobMonthStr, String dobDayStr, String roleName, boolean admin) {

		Date dob = new Date(new GregorianCalendar(Integer.parseInt(dobYearStr), Integer.parseInt(dobMonthStr),
				Integer.parseInt(dobDayStr)).getTime().getTime());

		userDao.updateUser(String.valueOf(user.getId()),  password, firstName, lastName,  email,  dob, admin, gender, roleName);
		
		String message = "This user " + user.getUsername() + " has been updated" ;
		
		wallService.addWallMessage(user, SectionEnum.Admin, message);	
	}

	
	@Transactional
	public UserBean findUserByUsername(String username) {
		UserBean userBean = null;
		UserDomain userDomain = userDao.findUserByUsername(username);

		if (userDomain != null) {
			userBean = new UserBean(userDomain);
		}
						
		return userBean;
	}
	
	@Transactional
	public UserBean findUserByUserid(long userId) {
		UserDomain userDomain = userDao.findUser(String.valueOf(userId));
		UserBean userBean = new UserBean(userDomain);
		
		return userBean;
	}

	
}
