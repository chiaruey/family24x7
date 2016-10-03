package com.family.test.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.family.config.AppConfig;
import com.family.service.WallService;
import com.family.service.bean.WallMessageBean;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {AppConfig.class})
public class WallServiceTest {
	
	@Autowired
	private WallService wallService;
	
	private Logger  logger = Logger.getLogger("com.family.test.service");
	
	@Test
	public void testFindFamilyWallMessages_obama() {
		long familyId = 150502175901125L;
		List<WallMessageBean> wallMessageList = null;
		try {
			wallMessageList = wallService.findFamilyWallMessages(familyId);
			
			logger.debug("The wall messages for the obama family");
			
			for (WallMessageBean wm : wallMessageList) {
				logger.debug(wm);
			}
			
		} catch(Exception e) {
			logger.error("fail to find wall message list for family id " + familyId, e);
		}
		
		assertNotNull(wallMessageList);
	}

}
