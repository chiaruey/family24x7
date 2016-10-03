package com.family.db.dao;

import java.util.List;

import com.family.db.domain.MoneyTransactionTypeDomain;
import com.family.enums.IoeEnum;

public interface MoneyTransactionTypeDao {

	/**
	 * Add a single transaction type
	 */
	MoneyTransactionTypeDomain addMoneyTransactionType(String name, IoeEnum ioe, String familyId, boolean active);
	
	void updTracTypeName(String transTypeId, String transTypeName);
	
	/**
	 * Find Money transaction type by id
	 */
	MoneyTransactionTypeDomain findMoneyTransactionType(String id);
	
	/**
	 * Find Money transaction type by name
	 */
	MoneyTransactionTypeDomain findMoneyTransactionTypeByName(String name);
	
	/**
	 * Return all default transaction types
	 */
//	List<MoneyTransactionTypeDomain> getDefaultTransactionTypeList();
	
	
	/**
	 * Return all family transaction types
	 */
	List<MoneyTransactionTypeDomain> findFamilyTransactionTypeList(String familyId);
	
	/**
	 * Return all family transaction types
	 */
//	List<MoneyTransactionTypeDomain> findFamilyTransactionTypeList(String familyId, String ioe);


	/**
	 * Remove a single money transaction type
	 */
	void removeMoneyTransactionType(String id);
	
	void activateTracType(String transTypeId, boolean activate);
	
}
