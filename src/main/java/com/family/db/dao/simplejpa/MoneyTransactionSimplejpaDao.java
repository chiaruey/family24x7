package com.family.db.dao.simplejpa;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.family.db.dao.MoneyTransactionDao;
import com.family.db.domain.MoneyTransactionDomain;
import com.family.db.util.DbUtils;
import com.family.exception.DaoException;

@Repository
public class MoneyTransactionSimplejpaDao implements MoneyTransactionDao {

	private static final String QUERY_SELECT_FAMILY_TRANSACTIONS = 
			"SELECT o FROM MoneyTransactionDomain o " +
			" WHERE o.familyId = :familyId " + 
			"   AND o.transactionDate >= :beginDate " + 
			"   AND o.transactionDate <  :endDate " + 
			" ORDER BY o.transactionDate ";
	

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@CacheEvict(value="familyTransactions", allEntries=true)
	public MoneyTransactionDomain addMoneyTransaction(String familyId, String tracTypeId, Date tracDate, String comments, BigDecimal amount) {
		EntityManager em = null;
		
		MoneyTransactionDomain mt = null;

		try {
			em = entityManagerFactory.createEntityManager();
			mt = new MoneyTransactionDomain();
			mt.setId(DbUtils.nextSequenceId());
        	String now = DbUtils.getCurrentTimestamp();
        	mt.setUpdated(now);
        	mt.setCreated(now);

        	mt.setFamilyId(familyId);
        	mt.setTransactionTypeId(tracTypeId);
        	mt.setTransactionDate(DbUtils.encodeDate(tracDate));
        	mt.setComments(comments);
        	mt.setAmount(DbUtils.printAmount(amount));
        	
			em.persist(mt);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return mt;
	}
	
	@CacheEvict(value="familyTransactions", allEntries=true)
	public MoneyTransactionDomain updMoneyTransaction(String id, Date tracDate, String comments, BigDecimal amount) {
		MoneyTransactionDomain mt = findMoneyTransaction(id);
		
		EntityManager em = null;
		
		try {
			
			em = entityManagerFactory.createEntityManager();
        	String now = DbUtils.getCurrentTimestamp();
        	mt.setUpdated(now);
        	mt.setCreated(now);

        	mt.setTransactionDate(DbUtils.encodeDate(tracDate));
        	mt.setComments(comments);
        	mt.setAmount(DbUtils.printAmount(amount));			
			em.persist(mt);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return mt;
		
	}
	
	public MoneyTransactionDomain findMoneyTransaction(String id) {
		EntityManager em = null;
		MoneyTransactionDomain mt = null;
		try {
			em = entityManagerFactory.createEntityManager();
			mt = em.find(MoneyTransactionDomain.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
		return mt;
		
	}
	

	@SuppressWarnings("unchecked")
	@Cacheable(value="familyTransactions", key="T(java.lang.String).valueOf(#familyId).concat('-').concat(#year).concat(#month)")
	public List<MoneyTransactionDomain> findFamilyTransactions(String familyId, int year, int month) {
		EntityManager em = null;
		List<MoneyTransactionDomain> mtList = null;
		
		try {
			Date beginDate = new GregorianCalendar(year, month, 1).getTime();
			Calendar endCalendar = new GregorianCalendar(year, month, 1);
			endCalendar.add(Calendar.MONTH, 1);
			Date endDate = endCalendar.getTime();
			
			em = entityManagerFactory.createEntityManager();
			
			Query query = em.createQuery(QUERY_SELECT_FAMILY_TRANSACTIONS);
			query.setParameter("familyId", familyId);		
			query.setParameter("beginDate", DbUtils.encodeDate(beginDate));
			query.setParameter("endDate", DbUtils.encodeDate(endDate));
			mtList = (List<MoneyTransactionDomain>) query.getResultList();;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
		return mtList;
		
	}
	
	@CacheEvict(value="familyTransactions", allEntries=true)
	public void deleteMoneyTransaction(String id) {
		EntityManager em = null;
		MoneyTransactionDomain mt = null;
		try {
			em = entityManagerFactory.createEntityManager();
			mt = em.find(MoneyTransactionDomain.class, id);
			em.remove(mt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		
	}
	
}
