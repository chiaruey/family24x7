package com.family.db.dao.simplejpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.family.db.dao.AddressDao;
import com.family.db.domain.AddressDomain;
import com.family.db.util.DbUtils;

@Repository
public class AddressSimplejpaDao implements AddressDao {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	/**
	 * Add new address to SimpleDB
	 */
	public AddressDomain addAddress(String street, String city, String state,
			String country, String zip) {

		EntityManager em = null;
		AddressDomain address = new AddressDomain();

		try {
			em = entityManagerFactory.createEntityManager();
			
			address.setId(DbUtils.nextSequenceId());
        	String now = DbUtils.getCurrentTimestamp();
        	address.setUpdated(now);
        	address.setCreated(now);

			address.setStreet(street);
			address.setState(state);
			address.setCity(city);
			address.setCountry(country);
			address.setZip(zip);
			em.persist(address);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return address;
	}


	/**
	 * Update an existing address in SimpleDB
	 */
	public AddressDomain updateAddress(String id, String street, String city, String state,
			String country, String zip) {
		EntityManager em = null;
		
		AddressDomain address = null;

		try {
			em = entityManagerFactory.createEntityManager();
			address = em.find(AddressDomain.class, id);
        	String now = DbUtils.getCurrentTimestamp();
        	address.setUpdated(now);
			address.setStreet(street);
			address.setState(state);
			address.setCity(city);
			address.setCountry(country);
			address.setZip(zip);
			em.persist(address);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
		return address;
	}
	
	/**
	 * Remove an existing address in SimpleDB
	 */
	public void removeAddress(String id) {
		EntityManager em = null;

		try {
			em = entityManagerFactory.createEntityManager();
			AddressDomain address = em.find(AddressDomain.class, id);
			em.remove(address);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
	}
	
	/**
	 * Get an address
	 */
	public AddressDomain findAddress(String id) {
		
		AddressDomain address = null;
		
		EntityManager em = null;

		try {
			em = entityManagerFactory.createEntityManager();
			address = em.find(AddressDomain.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
		return address;

		
	}

}
