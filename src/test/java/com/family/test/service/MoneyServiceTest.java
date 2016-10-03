package com.family.test.service;

import java.util.Calendar;
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
import com.family.service.MoneyService;
import com.family.service.bean.FamilyBean;
import com.family.service.bean.MoneyTransactionBean;
import com.family.service.bean.MoneyTransactionTypeBean;
import com.family.service.bean.UserBean;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { AppConfig.class})
public class MoneyServiceTest {
	
	@Autowired 
	private MoneyService moneyService;
	
	@Autowired
	private AccountService accountService;
	
	private Logger  logger = Logger.getLogger("com.family.test.service");
	
	@Test
	public void testFindStandardTransitionType() {
		List<MoneyTransactionTypeBean> mttbList = moneyService.findStandardTransactionType();
		assertNotNull(mttbList);
		logger.debug("testFindStandardTransitionType> Standard Money Transaction Type List");
		for (MoneyTransactionTypeBean mttb: mttbList) {
			logger.debug("testFindStandardTransitionType> " + mttb);
		}
	}
	
	/*
	 * List<MoneyTransactionBean> findMonthlyTransactions(long familyId, int year, int month)
	 */
	@Test
	public void testFindMonthlyTransactions() {
		
		String username = "barrackobama";
		
		UserBean user = accountService.findUserByUsername(username);
		logger.debug("testFindMonthlyTransactions> user = " + user);
		
		FamilyBean family = accountService.findFamily(user.getFamilyId());
		logger.debug("testFindMonthlyTransactions> family = " + family);

		List<MoneyTransactionBean> moneyTransactionList = null;
		Exception exception = null;
		
		try {
			moneyTransactionList = moneyService.findMonthlyTransactions(family.getId(), 2015, Calendar.MAY);
			
			logger.debug("testFindMonthlyTransactions> moneyTransactionList size = " + moneyTransactionList.size());
			for (MoneyTransactionBean mtb: moneyTransactionList) {
				logger.debug("testFindMonthlyTransactions>" + mtb);
			}
						
		} catch(Exception e) {
			logger.error("testFindMonthlyTransactions> fail to find MonthlyTransactions: " , e);
			exception = e;
		}
		
		assertNull(exception);
		assertNotNull(moneyTransactionList);

		
	}

}
