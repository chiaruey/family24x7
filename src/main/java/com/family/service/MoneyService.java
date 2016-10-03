package com.family.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.family.enums.IoeEnum;
import com.family.service.bean.FamilyBean;
import com.family.service.bean.MoneyTransactionBean;
import com.family.service.bean.MoneyTransactionTypeBean;
import com.family.service.bean.UserBean;
import com.family.web.dto.MonthlyMoneyDto;

public interface MoneyService {

	// //////////////////////
	// Monetary Transition

	List<MoneyTransactionBean> findMonthlyTransactions(long familyId, int year,
			int month);
	
//	List<MoneyTransactionBean> findFamilyTransactions(long familyId, Date beginDate, Date endDate);

	MoneyTransactionBean findMoneyTransaction(long familyId, long tracId);
	
	void addMoneyTransaction(FamilyBean family, Date tranDate, String transType,
			String comments, BigDecimal amount, UserBean user);

	void updMoneyTransaction(long id, Date tranDate, String comments,
			BigDecimal amount, UserBean user);

	MoneyTransactionBean getMoneyTransaction(long familyId, long id);

	void deleteMoneyTransition(long id, UserBean user);

	// ///////////////////
	// Populate the web page
	
	public MonthlyMoneyDto getMonthlyMoney(long familyId, int year, int month);

	// /////////////////
	// Transition Type

	void addFamilyTracType(long familyId, String ioe, String name);

	/**
	 * Return family specific monetary transition type
	 */
	List<MoneyTransactionTypeBean> findAvailableTransactionTypes(long familyId);
	List<MoneyTransactionTypeBean> findAvailableTransactionTypes(long familyId, IoeEnum ioe) ;
	List<MoneyTransactionTypeBean> findStandardTransactionType();
	List<MoneyTransactionTypeBean> findFamilyTransactionTypes(long familyId);

	MoneyTransactionTypeBean findTransactionTypeById(long familyId, long id);

/*	MoneyTransactionTypeBean findTransactionTypeById(long id);*/
	
	void updateMoneyTransTypeName(long transTypeId, String transTypeName, UserBean user);

	void deleteFamilyTracType(long id, UserBean user);
	
	void activateFamilyTracType(long transTypeId, UserBean user, boolean activate);
}
