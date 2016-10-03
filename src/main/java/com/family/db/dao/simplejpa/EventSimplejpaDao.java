package com.family.db.dao.simplejpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.family.db.dao.EventDao;
import com.family.db.domain.EventDomain;
import com.family.db.util.DbUtils;

@Repository
public class EventSimplejpaDao implements EventDao {

	private static final String QUERY_FIND_EVENTS_BY_FAMILY = 
			"SELECT o FROM EventDomain o "
			+ "WHERE o.familyId = :familyId "
			+ "AND o.endTime >= :fromTime "
			+ "AND o.startTime <= :toTime";

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	private Logger  logger = Logger.getLogger("com.family.db");

	@SuppressWarnings("unchecked")
	public List<EventDomain> findFamilyEvents(String familyId, Date fromTime, Date toTime) {
		String fromTimeStr = DbUtils.encodeDate(fromTime);
		String toTimeStr = DbUtils.encodeDate(toTime);
		logger.debug("findFamilyEvents> familyId = " + familyId + ", fromTimeStr = " + fromTimeStr + ", toTimeStr = " + toTimeStr);
		List<EventDomain> eventList = null ;
		EntityManager em = null;
		
		try {
			em = entityManagerFactory.createEntityManager();
			Query query = em.createQuery(QUERY_FIND_EVENTS_BY_FAMILY);
			
			query.setParameter("familyId", familyId);
			query.setParameter("fromTime", fromTimeStr);
			query.setParameter("toTime", toTimeStr);

			eventList = (List<EventDomain>) query.getResultList();		
		} catch (Exception e) {
			logger.error("fail to find family events", e);		
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return eventList;		
		
	}
	
	public EventDomain addNewEvent(String familyId, Date startTime, Date endTime, String title, String description, Boolean allDay) {
		EntityManager em = null;
		
		EventDomain event = new EventDomain();

		try {
			em = entityManagerFactory.createEntityManager();
			
			event.setId(DbUtils.nextSequenceId());
        	String now = DbUtils.getCurrentTimestamp();
        	event.setUpdated(now);
        	event.setCreated(now);
        	
        	event.setFamilyId(familyId);
        	event.setStartTime(DbUtils.encodeDate(startTime));
        	event.setEndTime(DbUtils.encodeDate(endTime));
        	event.setTitle(title);
        	event.setDescription(description);
        	event.setAllDay(allDay.toString());
        	
			em.persist(event);
		} catch (Exception e) {
			logger.error("fail to addNewEvent", e);
		} finally {
			if (em != null) {
				em.close();
			}
		}	
		return event;
	}
	
	public EventDomain updateEvent(String id, Date startTime, Date endTime, String title, String description, Boolean allDay) {
		EntityManager em = null;
		
		EventDomain event = new EventDomain();

		try {
			em = entityManagerFactory.createEntityManager();
			event = this.findEvent(id);

        	String now = DbUtils.getCurrentTimestamp();
        	event.setUpdated(now);
        	
        	event.setStartTime(DbUtils.encodeDate(startTime));
        	event.setEndTime(DbUtils.encodeDate(endTime));
        	event.setTitle(title);
        	event.setDescription(description);
        	event.setAllDay(allDay.toString());
        	
			em.persist(event);
		} catch (Exception e) {
			logger.error("fail to updateEvent", e);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
		return event;
		
	}

	public void deleteEvent(String id) {
		EntityManager em = null;

		try {
			em = entityManagerFactory.createEntityManager();
			EventDomain event = em.find(EventDomain.class, id);
			em.remove(event);
		} catch (Exception e) {
			logger.error("fail to deleteEvent", e);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
	}
	
	public EventDomain findEvent(String id) {
		EntityManager em = null;
		EventDomain event = null;
		try {
			em = entityManagerFactory.createEntityManager();
			event = em.find(EventDomain.class, id);
		} catch (Exception e) {
			logger.error("fail to findEvent", e);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
		return event;
		
	}

}
