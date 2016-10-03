package com.family.test.db.dao.simplejpa;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
//import java.util.List;
//
//import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.family.config.SimpleDbConfig;
import com.family.db.dao.MoneyTransactionTypeDao;
import com.family.db.domain.MoneyTransactionTypeDomain;
import com.family.enums.StandardMoneyTransactionEnum;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {
		SimpleDbConfig.class})
public class MoneyTransactionTypeSimplejpaDaoTest {

	@Autowired
	private MoneyTransactionTypeDao mttDao;
	
	private Logger  logger = Logger.getLogger("com.family.test.db");

	@Test
	public void testFindMoneyTracTypeByName() {

		MoneyTransactionTypeDomain gasTracType = null;
		try {
			String gas = StandardMoneyTransactionEnum.Gas.name();
			gasTracType = mttDao.findMoneyTransactionTypeByName(gas);
			logger.debug(">>> testFindMoneyTracTypeByName > gasTracType = "
							+ gasTracType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(gasTracType);
	}

}
