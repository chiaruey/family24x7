package com.family.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;

import com.family.db.dao.MoneyTransactionDao;
import com.family.db.dao.MoneyTransactionTypeDao;
import com.family.db.domain.MoneyTransactionDomain;
import com.family.db.domain.MoneyTransactionTypeDomain;
import com.family.enums.IoeEnum;
import com.family.enums.SectionEnum;
import com.family.enums.StandardMoneyTransactionEnum;
import com.family.exception.ServiceException;
import com.family.service.AccountService;
import com.family.service.MoneyService;
import com.family.service.WallService;
import com.family.service.bean.FamilyBean;
import com.family.service.bean.MoneyTransactionBean;
import com.family.service.bean.MoneyTransactionTypeBean;
import com.family.service.bean.UserBean;
import com.family.web.dto.FamilyTracTypeSummaryDto;
import com.family.web.dto.MonthlyMoneyDto;
import com.family.web.dto.SingleTracTypeSummaryDto;

@Service
public class MoneyServiceImpl implements MoneyService {
	
	private Logger  logger = Logger.getLogger("com.family.service");
	
	public static final String INCOME_TRANSITION_TYPE = "I";

	public static final String EXPENSE_TRANSITION_TYPE = "E";
	
	public static final String NEW_INCOME = "newIncome";
	public static final String NEW_EXPENSE = "newExpense";
	
	@Autowired
	private MoneyTransactionDao moneyTransactionDao;
	
	@Autowired
	private MoneyTransactionTypeDao moneyTransactionTypeDao;

	@Autowired
	private WallService wallService;	
	
	@Autowired
	private AccountService accountService;


	//////////////////////////////////////////////////////////
	// Monetary Transaction
	
	@Transactional
	public List<MoneyTransactionBean> findMonthlyTransactions(long familyId, int year, int month) {		
		List<MoneyTransactionBean> tracBeanList = new ArrayList<MoneyTransactionBean>();
		
		List<MoneyTransactionDomain> tracDomainList = moneyTransactionDao.findFamilyTransactions(String.valueOf(familyId), year, month);

		for (MoneyTransactionDomain tracDomain: tracDomainList) {
			logger.debug("MoneyServiceImpl>tracDomain = " + tracDomain);
			MoneyTransactionBean tracBean = populateMoneyTransactionBean(familyId, tracDomain);
			logger.debug("MoneyServiceImpl>tracBean = " + tracBean);
			MoneyTransactionTypeBean tracType = this.findTransactionTypeById(familyId, tracBean.getTracType().getId());
			tracBean.setTracType(tracType);
			tracBeanList.add(tracBean);
		}
		return tracBeanList;		
	}
	
	@Transactional
	public List<MoneyTransactionBean> findFamilyTransactions(long familyId, int year, int month) {
		List<MoneyTransactionBean> tracBeanList = new ArrayList<MoneyTransactionBean>();
		
		List<MoneyTransactionDomain> tracDomainList = moneyTransactionDao.findFamilyTransactions(String.valueOf(familyId), year, month);
		
		for (MoneyTransactionDomain tracDomain: tracDomainList) {
			logger.error("MoneyServiceImpl>tracDomain = " + tracDomain);
			MoneyTransactionBean tracBean = populateMoneyTransactionBean(familyId, tracDomain);
			logger.error("MoneyServiceImpl>tracBean = " + tracBean);
			MoneyTransactionTypeBean tracType = findTransactionTypeById(familyId, tracBean.getTracType().getId());
			tracBean.setTracType(tracType);
			tracBeanList.add(tracBean);
		}
		return tracBeanList;				

	}


	private MoneyTransactionBean populateMoneyTransactionBean(long familyId, MoneyTransactionDomain mtd) {
		MoneyTransactionBean mtb = new MoneyTransactionBean(mtd);
		mtb.setTracType(this.findTransactionTypeById(familyId, Long.parseLong(mtd.getTransactionTypeId())));
		mtb.setFamily(accountService.findFamily(Long.parseLong(mtd.getFamilyId())));
		return mtb;
	}
	
	@Transactional
	public MoneyTransactionBean getMoneyTransaction(long familyId, long id) {
		MoneyTransactionDomain mtd = moneyTransactionDao.findMoneyTransaction(String.valueOf(id));		
		logger.debug("getMoneyTransaction> mtd = " + mtd);
		MoneyTransactionBean mtb = new MoneyTransactionBean(mtd);	
		MoneyTransactionTypeBean tracType = findTransactionTypeById(familyId, NumberUtils.parseNumber(mtd.getTransactionTypeId(), Long.class));
		mtb.setTracType(tracType);
		return mtb;
	}
	
	@Transactional
	public void addMoneyTransaction(FamilyBean family, Date tracDate, String transType, String comments, BigDecimal amount, UserBean user) {
		try {
			long transTypeId = -1L;
			
			transTypeId = Long.parseLong(transType.replaceAll("[IEie]", ""));
			
			MoneyTransactionTypeBean tracType = this.findTransactionTypeById(family.getId(), transTypeId);
			
			moneyTransactionDao.addMoneyTransaction(String.valueOf(family.getId()), String.valueOf(tracType.getId()), tracDate, comments, amount);
			
			String message = user.getFirstName() + " has added moneytary transition: "
					+ tracType.getName() + (StringUtils.isEmpty(comments)? "" : " - " + comments);
			
			wallService.addWallMessage(user, SectionEnum.Money, message);			
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException("Fail on addMoneyTransaction:", e);
		}
	}
	
	/**
	 * Since getFamilyTransactins is cached, it is supposed to be faster
	 */
	@Transactional
	public MoneyTransactionTypeBean findTransactionTypeById(long familyId, long id) {
		MoneyTransactionTypeBean returnValue = null;
		List<MoneyTransactionTypeBean> tracTypeList = findAvailableTransactionTypes(familyId);		
		for (MoneyTransactionTypeBean tracType: tracTypeList) {
			if (tracType.getId() == id) {
				returnValue = tracType;
				break;
			}
		}	
		return returnValue;
	}
	
	@Transactional
	public MoneyTransactionBean findMoneyTransaction(long familyId, long tracId) {	
		MoneyTransactionDomain tracDomain = moneyTransactionDao.findMoneyTransaction(String.valueOf(tracId));
		MoneyTransactionBean tracBean = populateMoneyTransactionBean(familyId, tracDomain);
		MoneyTransactionTypeBean tracType = this.findTransactionTypeById(familyId, tracBean.getTracType().getId());
		tracBean.setTracType(tracType);
		
		return tracBean;
	}
	
	@Transactional
	public void updMoneyTransaction(long tracId, Date tranDate, String comments, BigDecimal amount, UserBean user) {
		MoneyTransactionBean trac = this.findMoneyTransaction(user.getFamilyId(), tracId);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String message = user.getFirstName() + " has updated transaction for "
				+ trac.getComments() + " on "
				+ sdf.format(trac.getTracDate());

		moneyTransactionDao.updMoneyTransaction(String.valueOf(tracId), tranDate, comments, amount);
		wallService.addWallMessage(user, SectionEnum.Money, message);		
	}
	
	@Transactional
	public void updateMoneyTransTypeName(long transTypeId, String transTypeName, UserBean user) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String message = user.getFirstName() + " has updated transition type name to  "
				+ transTypeName+ " on "
				+ sdf.format(Calendar.getInstance().getTime());

		moneyTransactionTypeDao.updTracTypeName(String.valueOf(transTypeId), transTypeName);
		wallService.addWallMessage(user, SectionEnum.Money, message);			
	}
	
	@Transactional
	public void deleteMoneyTransition(long id, UserBean user) {
		MoneyTransactionBean mtb = this.getMoneyTransaction(user.getFamilyId(), id);
		String message = user.getFirstName() + " has deleted transaction for "
				+ mtb.getTracType().getName() + " on "
				+ mtb.getTracDate();
		
		moneyTransactionDao.deleteMoneyTransaction(String.valueOf(id));
		wallService.addWallMessage(user, SectionEnum.Money, message);		
	}
	

	@Transactional
	public void deleteFamilyTracType(long id, UserBean user) {
		List<MoneyTransactionTypeDomain> familyTracTypes = moneyTransactionTypeDao.findFamilyTransactionTypeList(String.valueOf(user.getFamilyId()));
		
		boolean tracFound = false; 
		for (MoneyTransactionTypeDomain ftt: familyTracTypes) {
			if (Long.parseLong(ftt.getId()) == id) {
				tracFound = true;

				String message = user.getFirstName() + " has deleted family transaction type  " + ftt.getName();
				
				moneyTransactionTypeDao.removeMoneyTransactionType(ftt.getId());
				wallService.addWallMessage(user, SectionEnum.Money, message);				
				return;
			}			
		}
		
		if (!tracFound) {
			throw new RuntimeException(" trac type " + id + " does not belong to family " + user.getFamilyId());
		}

	}
	
	///////////////////////////////////////////////
	// Transaction Summary
	

	@Transactional
	public MonthlyMoneyDto getMonthlyMoney(long familyId, int year, int month) {


		MonthlyMoneyDto monthlyMoney = new MonthlyMoneyDto(year, month);
		List<MoneyTransactionTypeBean> allTracTypes = findAvailableTransactionTypes(familyId);
		
		monthlyMoney.setTransitionTypes(allTracTypes);

		List<MoneyTransactionBean> allTracs = this.findFamilyTransactions(familyId, year, month);
		
		FamilyTracTypeSummaryDto expenseTrancSummary = getMonthlyFamilyTransacSummary(familyId, year, month, IoeEnum.E.name(), allTracs, allTracTypes);

		FamilyTracTypeSummaryDto incomeTrancSummary = getMonthlyFamilyTransacSummary(familyId, year, month, IoeEnum.I.name(), allTracs, allTracTypes);

		monthlyMoney.setTransitions(allTracs);
		
		List<SingleTracTypeSummaryDto> expenseTracSummaryList = new ArrayList<SingleTracTypeSummaryDto>();
		expenseTracSummaryList.addAll(expenseTrancSummary.getTracTypeSummaryMap().values());
		Collections.sort(expenseTracSummaryList);
		monthlyMoney.setExpenseTrancSummary(expenseTracSummaryList);
		
		List<SingleTracTypeSummaryDto> incomeTracSummaryList = new ArrayList<SingleTracTypeSummaryDto>();
		incomeTracSummaryList.addAll(incomeTrancSummary.getTracTypeSummaryMap().values());
		Collections.sort(incomeTracSummaryList);

		monthlyMoney.setIncomeTrancSummary(incomeTracSummaryList);

		return monthlyMoney;
	}

	private FamilyTracTypeSummaryDto getMonthlyFamilyTransacSummary(long familyId, int year, int month, String ioe, 
			List<MoneyTransactionBean> allTracs, List<MoneyTransactionTypeBean> allTracTypes) {

		List<MoneyTransactionBean> tracList = new ArrayList<MoneyTransactionBean>();

		for (MoneyTransactionBean trac: allTracs) {
			if (trac.getTracType().getIoe().equals(ioe)) {
				tracList.add(trac);
			}
		}

		Map<Long, SingleTracTypeSummaryDto> tracSummaryMap = new HashMap<Long, SingleTracTypeSummaryDto>(); 
			
		for (MoneyTransactionTypeBean tracType: allTracTypes) {
			
			if (ioe.equals(tracType.getIoe())) {
				tracSummaryMap.put(tracType.getId(), new SingleTracTypeSummaryDto(tracType));
			}
			
		}

		
		for (MoneyTransactionBean mt: tracList) {
			Long key = mt.getTracType().getId();
			logger.debug("ftm = " + tracSummaryMap);
			logger.debug("key = " + key);
			SingleTracTypeSummaryDto dto = tracSummaryMap.get(key);
			logger.debug("dto = " + dto);
			dto.add(mt);
		}
		
		BigDecimal totalAmount = new BigDecimal(0, new MathContext(2));
		
		for (SingleTracTypeSummaryDto sts: tracSummaryMap.values()) {
			totalAmount = totalAmount.add(sts.getTotalAmount());
		}
			
		for (SingleTracTypeSummaryDto sts: tracSummaryMap.values()) {
			if (totalAmount.floatValue()  > 0) {
				sts.setPct(sts.getTotalAmount().multiply(BigDecimal.valueOf(100)).divide(totalAmount, 2, RoundingMode.HALF_UP));
			}
		}
		
		Map<Long, SingleTracTypeSummaryDto> moneyTracMap = new HashMap<Long, SingleTracTypeSummaryDto>(); 
		// Remove empty transaction type
		for (Map.Entry<Long, SingleTracTypeSummaryDto> tracSummary: tracSummaryMap.entrySet()) {
			if (tracSummary.getValue().getTracCount() > 0) {
				moneyTracMap.put(tracSummary.getKey(), tracSummary.getValue());
			}
			
		}
		
		Date beginDate = new GregorianCalendar(year, month, 1).getTime();
		Calendar endCalendar = new GregorianCalendar(year, month, 1);
		endCalendar.add(Calendar.MONTH, 1);
		Date endDate = endCalendar.getTime();
		return new FamilyTracTypeSummaryDto(familyId, beginDate, endDate, moneyTracMap);		

	}

	

	
	///////////////////////////////////////////
	// Transition Type
	
	@Transactional
	public void addFamilyTracType(long familyId, String ioe, String name) {
		moneyTransactionTypeDao.addMoneyTransactionType(name, IoeEnum.valueOf(ioe), String.valueOf(familyId), true);	
	}
	
	public List<MoneyTransactionTypeBean> findStandardTransactionType(){

		List<MoneyTransactionTypeBean> mttbList = new ArrayList<MoneyTransactionTypeBean>();
		
		for (StandardMoneyTransactionEnum smte: StandardMoneyTransactionEnum.values()) {
			mttbList.add(new MoneyTransactionTypeBean(smte));
		}
		
		return mttbList;
	}
	
	@Transactional
	public List<MoneyTransactionTypeBean> findAvailableTransactionTypes(long familyId) {
		List<MoneyTransactionTypeDomain> familyTransitionTypes = moneyTransactionTypeDao.findFamilyTransactionTypeList(String.valueOf(familyId));
		List<MoneyTransactionTypeBean> standardTransitionTypes = findStandardTransactionType();
		List<MoneyTransactionTypeBean> availableTransitionTypes = new ArrayList<MoneyTransactionTypeBean>();
		
		for (MoneyTransactionTypeDomain tracTypeDomain: familyTransitionTypes) {
			availableTransitionTypes.add(new MoneyTransactionTypeBean(tracTypeDomain));
		}
		availableTransitionTypes.addAll(standardTransitionTypes);
		return availableTransitionTypes;
	}
	
	@Transactional
	public List<MoneyTransactionTypeBean> findFamilyTransactionTypes(long familyId) {
		List<MoneyTransactionTypeDomain> domainList = moneyTransactionTypeDao.findFamilyTransactionTypeList(String.valueOf(familyId));
			
		List<MoneyTransactionTypeBean> returnList = new ArrayList<MoneyTransactionTypeBean>();
		
		for (MoneyTransactionTypeDomain domain: domainList) {
			returnList.add(new MoneyTransactionTypeBean(domain));
		}

		return returnList;
	}

	@Transactional
	public List<MoneyTransactionTypeBean> findAvailableTransactionTypes(long familyId, IoeEnum ioe) {
		List<MoneyTransactionTypeBean> allTracTypes = this.findAvailableTransactionTypes(familyId);
		
		List<MoneyTransactionTypeBean> returnList = new ArrayList<MoneyTransactionTypeBean>();
		for (MoneyTransactionTypeBean tracType: allTracTypes) {
			if (tracType.getIoe().equalsIgnoreCase(ioe.name())) {
				returnList.add(tracType);
			}		
		}

		return returnList;
	}



	/**
	 * Need To Use EHCache Later On
	 * @return
	 */
	public List<MoneyTransactionTypeBean> getStandardTransitionType(IoeEnum ioe) {
		List<MoneyTransactionTypeBean> mttbList = new ArrayList<MoneyTransactionTypeBean>();
		
		for (StandardMoneyTransactionEnum smte: StandardMoneyTransactionEnum.values()) {
			if (smte.getIoe() == ioe) {
				mttbList.add(new MoneyTransactionTypeBean(smte));
			}	
		}
		
		return mttbList;
	}

	@Transactional
	public void activateFamilyTracType(long transTypeId, UserBean user, boolean activate) {
		List<MoneyTransactionTypeDomain> familyTracTypes = moneyTransactionTypeDao.findFamilyTransactionTypeList(String.valueOf(user.getFamilyId())); 
		
		boolean tracFound = false; 
		for (MoneyTransactionTypeDomain ftt: familyTracTypes) {
			if (Long.parseLong(ftt.getId()) == transTypeId) {
				tracFound = true;

				String activateStr = activate ? "activated" : "deactivated";
				String message = user.getFirstName() + " has " + activateStr + " family transaction type  " + ftt.getName();
				
				moneyTransactionTypeDao.activateTracType(String.valueOf(transTypeId), activate);
				
				wallService.addWallMessage(user, SectionEnum.Money, message);				
				break;
			}			
		}
		
		if (!tracFound) {
			throw new RuntimeException(" trac type " + transTypeId + " does not belong to family " + user.getFamilyId());
		}
		
	}

}
