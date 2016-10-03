package com.family.db.dao.simplejpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.family.db.dao.WallMessageDao;
import com.family.db.domain.WallMessageDomain;
import com.family.db.util.DbUtils;
import com.family.enums.SectionEnum;

@Repository
public class WallMessageSimplejpaDao implements WallMessageDao {
	
	private static final String QUERY_FIND_WALL_MESSAGES_BY_FAMILY = "select o from WallMessageDomain o where o.familyId = :familyId";

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Cacheable(value="wallMessage")
	public WallMessageDomain addWallMessage(String userId, String familyId, SectionEnum sectionEnum,
			String message) {

		EntityManager em = null;
		
		WallMessageDomain wm = new WallMessageDomain();

		try {
			em = entityManagerFactory.createEntityManager();
			
			wm.setId(DbUtils.nextSequenceId());
        	String now = DbUtils.getCurrentTimestamp();
        	wm.setUpdated(now);
        	wm.setCreated(now);
        	
        	wm.setUserId(userId);
        	wm.setFamilyId(familyId);
        	wm.setSection(sectionEnum.name());
        	wm.setMessage(message);
        	
			em.persist(wm);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
		return wm;
		
	}


	@SuppressWarnings("unchecked")
	public List<WallMessageDomain> findFamilyWallMessages(String familyId) {
		List<WallMessageDomain> wallMessageList = null ;
		EntityManager em = null;
		
		try {
			em = entityManagerFactory.createEntityManager();
			Query query = em.createQuery(QUERY_FIND_WALL_MESSAGES_BY_FAMILY);
			query.setParameter("familyId", familyId);
			wallMessageList = (List<WallMessageDomain>) query.getResultList();		
		} catch (Exception e) {
			e.printStackTrace();			
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return wallMessageList;		
	}

	/**
	 * Delete a wall message
	 * 
	 * @param id
	 *            the wall message id to be deleted
	 */
	public void deleteWallMessage(String id) {
		EntityManager em = null;

		try {
			em = entityManagerFactory.createEntityManager();
			WallMessageDomain wm = em.find(WallMessageDomain.class, id);
			em.remove(wm);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
	}


}
