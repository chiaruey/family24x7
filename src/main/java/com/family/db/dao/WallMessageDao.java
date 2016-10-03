package com.family.db.dao;

import java.util.List;

import com.family.db.domain.WallMessageDomain;
import com.family.enums.SectionEnum;

public interface WallMessageDao {

	/**
	 * Add a new Wall Message on the persistence layer
	 */
	WallMessageDomain addWallMessage(String userId, String familyId, SectionEnum sectionEnum,
			String message);

	List<WallMessageDomain> findFamilyWallMessages(String familyId);

	/**
	 * Delete a wall message
	 * 
	 * @param id
	 *            the wall message id to be deleted
	 */
	void deleteWallMessage(String id);

}
