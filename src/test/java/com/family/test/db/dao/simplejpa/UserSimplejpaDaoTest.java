package com.family.test.db.dao.simplejpa;

import static org.junit.Assert.assertNotNull;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.family.config.SimpleDbConfig;
import com.family.db.dao.UserDao;
import com.family.db.domain.UserDomain;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {
		SimpleDbConfig.class})
public class UserSimplejpaDaoTest {
	
	@Autowired
	private UserDao userDao;
	
	private Logger  logger = Logger.getLogger("com.family.test.db");
	

	@Test
	public void testFindUser() {
		
		UserDomain user = null;
		try {
			String id = "150503163737336";
			user = userDao.findUser(id);
			logger.debug(">>> testFindUser > user = " + user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(user);
	}



}
