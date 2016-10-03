package com.family.db.dao.simplejpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.family.db.dao.FamilyDao;
import com.family.db.domain.FamilyDomain;
import com.family.db.util.DbUtils;
import com.family.util.MyUtils;

@Repository
public class FamilySimplejpaDao implements FamilyDao {

	private static final String QUERY_SELECT_FAMILY_BY_FAMILYNAME = "select o from FamilyDomain o where o.familyname = :familyname";

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public FamilyDomain addFamily(String househeadId, String addressId) {

		EntityManager em = null;
		
		FamilyDomain family = new FamilyDomain();

		try {
			em = entityManagerFactory.createEntityManager();
			family.setId(DbUtils.nextSequenceId());
        	String now = DbUtils.getCurrentTimestamp();
        	family.setUpdated(now);
        	family.setCreated(now);
			family.setAddressId(addressId);
			family.setHouseheadId(househeadId);
			em.persist(family);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
		return family;
	}
	
	/**
	 * find a family by familyname in SimpleDB
	 */
	@SuppressWarnings("unchecked")
	public FamilyDomain findFamilyByFamilyname(String familyname) {
		EntityManager em = null;
		FamilyDomain family = null;
		try {
			em = entityManagerFactory.createEntityManager();
			Query query = em.createQuery(QUERY_SELECT_FAMILY_BY_FAMILYNAME);
			query.setParameter("familyname", MyUtils.upperCase(familyname));		
			List<FamilyDomain> familyList = (List<FamilyDomain>) query.getResultList();
			System.out.println("familyList size = " + familyList.size());	
			if (familyList != null && familyList.size() > 0) {
				family = familyList.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
		return family;
		
	}
	
	@Cacheable(value="family", key="T(java.lang.String).valueOf(#id)")
	public FamilyDomain findFamilyById(String id) {
		EntityManager em = null;

		FamilyDomain family = null;
		try {
			em = entityManagerFactory.createEntityManager();
			family = em.find(FamilyDomain.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return family;
		
	}


}
