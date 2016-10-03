package com.family.db.dao.simplejpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.family.db.dao.MoneyTransactionTypeDao;
import com.family.db.domain.MoneyTransactionTypeDomain;
import com.family.db.util.DbUtils;
import com.family.enums.IoeEnum;

@Repository
public class MoneyTransactionTypeSimplejpaDao implements MoneyTransactionTypeDao {
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	private static final String QUERY_SELECT_FAMILY_TRANSACTION_TYPE = "select o from MoneyTransactionTypeDomain o where o.familyId = :familyId";
	
//	private static final String QUERY_SELECT_FAMILY_IOE_TRANSACTION_TYPE = "select o from MoneyTransactionTypeDomain o where o.familyId = :familyId and o.ioe = :ioe";

	private static final String QUERY_SELECT_MONEY_TRANSACTION_TYPE_BY_NAME = "select o from MoneyTransactionTypeDomain o where o.name = :name";
	
	@CacheEvict(value="familyTransactionType", allEntries=true)
	public MoneyTransactionTypeDomain addMoneyTransactionType(String name, IoeEnum ioe, String familyId, boolean active) {

		EntityManager em = null;
		
		MoneyTransactionTypeDomain mtt = new MoneyTransactionTypeDomain();

		try {
			em = entityManagerFactory.createEntityManager();
			
			mtt.setId(DbUtils.nextSequenceId());
        	String now = DbUtils.getCurrentTimestamp();
        	mtt.setUpdated(now);
        	mtt.setCreated(now);
        	
        	mtt.setActive(Boolean.valueOf(active).toString());
        	mtt.setFamilyId(familyId);
        	mtt.setIoe(ioe.name());
        	mtt.setName(name);
        	
			em.persist(mtt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
		return mtt;
	}
	
	public MoneyTransactionTypeDomain findMoneyTransactionType(String id) {
		EntityManager em = null;
		MoneyTransactionTypeDomain mtt = null;
		try {
			em = entityManagerFactory.createEntityManager();
			mtt = em.find(MoneyTransactionTypeDomain.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
		return mtt;
		
	}

	/**
	 * Update an existing address in SimpleDB
	 */
	public void removeMoneyTransactionType(String id) {
		EntityManager em = null;

		try {
			em = entityManagerFactory.createEntityManager();
			MoneyTransactionTypeDomain mtt = em.find(MoneyTransactionTypeDomain.class, id);
			em.remove(mtt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public MoneyTransactionTypeDomain findMoneyTransactionTypeByName(String name) {
		EntityManager em = null;
		MoneyTransactionTypeDomain moneyTracType = null;
		try {
			em = entityManagerFactory.createEntityManager();
			Query query = em.createQuery(QUERY_SELECT_MONEY_TRANSACTION_TYPE_BY_NAME);
			query.setParameter("name", name);		
			List<MoneyTransactionTypeDomain> moneyTracTypeList = (List<MoneyTransactionTypeDomain>) query.getResultList();
			moneyTracType = moneyTracTypeList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
		return moneyTracType;
		
	}
	
	@CacheEvict(value="familyTransactionType", allEntries=true)
	public void updTracTypeName(String transTypeId, String transTypeName) {
		EntityManager em = null;
		MoneyTransactionTypeDomain mtt = findMoneyTransactionType(transTypeId);
		
		try {
			em = entityManagerFactory.createEntityManager();
			
        	String now = DbUtils.getCurrentTimestamp();
        	mtt.setUpdated(now);
        	mtt.setCreated(now);
        	
        	mtt.setName(transTypeName);
        	
			em.persist(mtt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}
	
	@SuppressWarnings("unchecked")
	@Cacheable("familyTransactionType")
	public List<MoneyTransactionTypeDomain> findFamilyTransactionTypeList(String familyId) {
		List<MoneyTransactionTypeDomain> familyTracTypeList = null ;
		EntityManager em = null;
		
		try {
			em = entityManagerFactory.createEntityManager();
			Query query = em.createQuery(QUERY_SELECT_FAMILY_TRANSACTION_TYPE);
			query.setParameter("familyId", familyId);
			familyTracTypeList = (List<MoneyTransactionTypeDomain>) query.getResultList();		
		} catch (Exception e) {
			e.printStackTrace();			
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return familyTracTypeList;
		
	}
	
//	@SuppressWarnings("unchecked")
//	public List<MoneyTransactionTypeDomain> findFamilyTransactionTypeList(String familyId, String ioe) {
//		List<MoneyTransactionTypeDomain> familyTracTypeList = null ;
//		EntityManager em = null;
//		
//		try {
//			em = entityManagerFactory.createEntityManager();
//			Query query = em.createQuery(QUERY_SELECT_FAMILY_IOE_TRANSACTION_TYPE);
//			query.setParameter("ioe", ioe);
//			query.setParameter("familyId", familyId);
//			familyTracTypeList = (List<MoneyTransactionTypeDomain>) query.getResultList();		
//		} catch (Exception e) {
//			e.printStackTrace();			
//		} finally {
//			if (em != null) {
//				em.close();
//			}
//		}
//		return familyTracTypeList;
//		
//	
//	}
	
	@CacheEvict(value="familyTransactionType", allEntries=true)
	public void activateTracType(String transTypeId, boolean activate) {
		EntityManager em = null;
		MoneyTransactionTypeDomain mtt = findMoneyTransactionType(transTypeId);
		
		try {
			em = entityManagerFactory.createEntityManager();
			
			mtt.setId(DbUtils.nextSequenceId());
        	String now = DbUtils.getCurrentTimestamp();
        	mtt.setUpdated(now);
        	mtt.setCreated(now);
        	
        	mtt.setActive(Boolean.valueOf(activate).toString());
        	
			em.persist(mtt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		
	}

}
