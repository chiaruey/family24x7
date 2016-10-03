package com.family.service;

import java.util.List;

import com.family.enums.SectionEnum;
import com.family.service.bean.UserBean;
import com.family.service.bean.WallMessageBean;

public interface WallService {

	/**
	 * Get All wall messages for a single user.
	 */
	List<WallMessageBean> findFamilyWallMessages(long familyId);
	
	/**
	 * Delete Wall Message
	 */
	void deleteWallMessage(long id);
	
	/**
	 * Add Wall Message
	 */
	void addWallMessage(UserBean userBean, SectionEnum section, String message) ;
	
	/**
	 * Add Wall Message
	 */
	void addWallMessage(long userId, long familyId, SectionEnum section, String message) ;
	
}
