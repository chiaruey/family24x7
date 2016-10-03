package com.family.test.service;

import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.family.config.AppConfig;
import com.family.service.AccountService;
import com.family.service.bean.FamilyBean;
import com.family.service.bean.UserBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { AppConfig.class})
public class AccountServiceTest {
	
	@Autowired
	private AccountService accountService;
	
	private Logger  logger = Logger.getLogger("com.family.test.service");
		
	//////////////////////////
	// Find Family Member List
	
	@Test
	public void testFindFamily_obama() {
		FamilyBean family = null;
		long familyId = 150502175901125L;
		try {
			family = accountService.findFamily(familyId);
			logger.debug("findFamily_obama>" + family);
		} catch (Exception e) {
			logger.error("fail to find family for family id " + familyId, e);
		}
		assertNotNull(family);		
	}

	
	//////////////////////////
	// Find Family Member List
	
	@Test
	public void testFindFamilyMemberList_obama() {
		
		List<UserBean> familyMemberList = new ArrayList<UserBean>();
		long familyId = 150502175901125L;
		try {
			familyMemberList = accountService.findFamilyMemberList(familyId);
			if (familyMemberList != null) {
				logger.debug("Obama family members: ");
				for (UserBean familyMember: familyMemberList) {
					logger.debug(familyMember);
				}
			}
		} catch (Exception e) {
			logger.error("fail to find family member list for family id " + familyId, e);
		}
		
		assertNotNull(familyMemberList);		
	}
	
	/////////////
	// Find User

	@Test
	public void testFindUserByUsername_barrackobama() {
		UserBean user = null;
		String userName = "barrackobama";
		try {
			user = accountService.findUserByUsername(userName);		
		} catch (Exception e) {
			logger.error("fail to find " + userName, e);
		}

		logger.debug("testFindUserByUsername found " + userName + " = " + user);
		
		assertNotNull(user);
		
	}
	
}
