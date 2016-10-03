package com.family.db.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.family.db.domain.MoneyTransactionDomain;

public interface MoneyTransactionDao {

	MoneyTransactionDomain addMoneyTransaction(String familyId, String tracTypeId, Date tracDate, String comments, BigDecimal amount);
	MoneyTransactionDomain updMoneyTransaction(String id, Date tracDate, String comments, BigDecimal amount);
	void deleteMoneyTransaction(String id);
	MoneyTransactionDomain findMoneyTransaction(String id);

	List<MoneyTransactionDomain> findFamilyTransactions(String familyId, int year, int month);
//	List<MoneyTransactionDomain> findFamilyTransactions(String familyId, Date beginDate, Date endDate);	
	
//	List<MoneyTransactionDomain> findFamilyTransactions(String familyId, Date beginDate, Date endDate, String tracTypeId);

}
