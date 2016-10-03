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
import com.family.db.dao.MoneyTransactionDao;
import com.family.db.dao.MoneyTransactionTypeDao;
import com.family.db.domain.MoneyTransactionDomain;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {
		SimpleDbConfig.class})
public class MoneyTransactionSimplejpaDaoTest {

	@Autowired
	private MoneyTransactionDao mtDao;
	
	@Autowired
	private MoneyTransactionTypeDao mttDao;
	
	private Logger  logger = Logger.getLogger("com.family.test.db");

	@Test
	public void testFindMoneyTransaction() {

		MoneyTransactionDomain mt = null;
		try {
			String id = "150506171101998";
			mt = mtDao.findMoneyTransaction(id);
			
			logger.debug("Money Transaction = " + mt);

		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(mt);
	}
}
