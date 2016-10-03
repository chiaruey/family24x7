package com.family.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.family.db.dao.WallMessageDao;
import com.family.db.domain.WallMessageDomain;
import com.family.enums.SectionEnum;
import com.family.service.WallService;
import com.family.service.bean.UserBean;
import com.family.service.bean.WallMessageBean;

@Service
public class WallServiceImpl implements WallService {

	@Autowired
	private WallMessageDao wallMessageDao;

	@Transactional
	public List<WallMessageBean> findFamilyWallMessages(long familyId) {	
		List<WallMessageBean> wallMessages = new ArrayList<WallMessageBean>();
		List<WallMessageDomain> wmDomainList = wallMessageDao.findFamilyWallMessages(String.valueOf(familyId));
		if (wmDomainList != null && wmDomainList.size() > 0) {
			for (WallMessageDomain wmDomain : wmDomainList) {
				wallMessages.add(new WallMessageBean(wmDomain));
			}
		}
		return wallMessages;
	}
	
	@Transactional
	public void deleteWallMessage(long id) {
		wallMessageDao.deleteWallMessage(String.valueOf(id));
	}
	
	/**
	 * Insert a new record into the database wall_message table
	 */
	@Transactional
	public void addWallMessage(UserBean user, SectionEnum section, String message) {
		String userIdStr = String.valueOf(user.getId());
		String familyIdStr = String.valueOf(user.getFamilyId());
		wallMessageDao.addWallMessage(userIdStr, familyIdStr, section, message);
	}
	
	@Transactional
	public void addWallMessage(long userId, long familyId, SectionEnum section, String message) {
		wallMessageDao.addWallMessage(String.valueOf(userId), String.valueOf(familyId), section, message);
	}
	
}
