package com.family.db.dao.simplejpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.family.db.dao.UserDao;
import com.family.db.domain.AddressDomain;
import com.family.db.domain.FamilyDomain;
import com.family.db.domain.UserDomain;
import com.family.db.util.DbUtils;
import com.family.enums.GenderEnum;
import com.family.util.MyUtils;

@Repository
public class UserSimplejpaDao implements UserDao {

	private static final String QUERY_SELECT_USER_BY_USERNAME = "select o from UserDomain o where o.username = :username";

	private static final String QUERY_SELECT_USER_BY_FAMILYID = "select o from UserDomain o where o.familyId = :familyId";

	@Autowired
	private EntityManagerFactory entityManagerFactory;

/*	@CacheEvict(value = "familyMember", allEntries = true)
	public UserDomain addUser(String username, String password,
			String firstName, String lastName, String email, Date dob,
			boolean admin, GenderEnum gender, boolean active, String roleName) {

		EntityManager em = null;
		UserDomain user = new UserDomain();

		try {
			em = entityManagerFactory.createEntityManager();
			user.setId(DbUtils.nextSequenceId());
			String now = DbUtils.getCurrentTimestamp();
			user.setUpdated(now);
			user.setCreated(now);
			user.setUsername(MyUtils.lowerCase(username));
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setDob(DbUtils.encodeDate(dob));
			user.setAdmin(Boolean.valueOf(admin).toString());
			user.setGender(gender.name());
			user.setActive(Boolean.valueOf(active).toString());
			user.setRoleName(roleName);
			em.persist(user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return user;
	}
*/
	/**
	 * find a user in SimpleDB
	 */
	public UserDomain findUser(String id) {
		EntityManager em = null;

		UserDomain user = null;
		try {
			em = entityManagerFactory.createEntityManager();
			user = em.find(UserDomain.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return user;
	}

	/**
	 * find a user in SimpleDB
	 */
	@SuppressWarnings("unchecked")
	@Cacheable(value="familyMember", key="T(java.lang.String).valueOf(#username)")
	public UserDomain findUserByUsername(String username) {
		EntityManager em = null;
		UserDomain user = null;
		try {
			em = entityManagerFactory.createEntityManager();
			Query query = em.createQuery(QUERY_SELECT_USER_BY_USERNAME);
			query.setParameter("username", MyUtils.lowerCase(username));
			List<UserDomain> userList = (List<UserDomain>) query
					.getResultList();
			System.out.println("userList size = " + userList.size());
			if (userList != null && userList.size() > 0) {
				user = userList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return user;

	}

	/**
	 * find family member in SimpleDB
	 */
	@SuppressWarnings("unchecked")
	@Cacheable(value="familyMember", key="T(java.lang.String).valueOf(#familyId)")
	public List<UserDomain> findFamilyMemberList(String familyId) {
		EntityManager em = null;
		List<UserDomain> userList = new ArrayList<UserDomain>();
		try {
			em = entityManagerFactory.createEntityManager();
			Query query = em.createQuery(QUERY_SELECT_USER_BY_FAMILYID);
			query.setParameter("familyId", familyId);
			userList = (List<UserDomain>) query.getResultList();
			System.out.println("familyMemberList size = " + userList.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return userList;

	}

	/**
	 * Update a user in SimpleDB
	 */
	@CacheEvict(value = "familyMember", allEntries = true)
	public UserDomain updateUser(String id, String password, String firstName,
			String lastName, String email, Date dob, boolean admin,
			GenderEnum gender, boolean active, String roleName) {

		EntityManager em = null;
		UserDomain user = null;
		try {
			em = entityManagerFactory.createEntityManager();
			user = em.find(UserDomain.class, id);
			String now = DbUtils.getCurrentTimestamp();
			user.setUpdated(now);

			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setDob(DbUtils.encodeDate(dob));
			user.setAdmin(Boolean.valueOf(admin).toString());
			user.setGender(gender.name());
			user.setActive(String.valueOf(active));
			user.setRoleName(roleName);
			em.persist(user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return user;

	}

	@CacheEvict(value = "familyMember", allEntries = true)
	public UserDomain updateUser(String id, String firstName,
			String lastName, String email, String secureQuestionId,
			String secureQuestionAnswer) {
		EntityManager em = null;
		UserDomain user = null;
		try {
			em = entityManagerFactory.createEntityManager();
			user = em.find(UserDomain.class, id);
			String now = DbUtils.getCurrentTimestamp();
			user.setUpdated(now);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setSecureQuestionId(secureQuestionId);
			user.setSecureQuestionAnswer(secureQuestionAnswer);
			em.persist(user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return user;
	}
	
	@CacheEvict(value = "familyMember", allEntries = true)
	public UserDomain updateUser(String id, String firstName,
			String lastName, String password, String email, String secureQuestionId,
			String secureQuestionAnswer) {
		EntityManager em = null;
		UserDomain user = null;
		try {
			em = entityManagerFactory.createEntityManager();
			user = em.find(UserDomain.class, id);
			String now = DbUtils.getCurrentTimestamp();
			user.setUpdated(now);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPassword(password);
			user.setEmail(email);
			user.setSecureQuestionId(secureQuestionId);
			user.setSecureQuestionAnswer(secureQuestionAnswer);
			em.persist(user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return user;
	}

	@CacheEvict(value = "familyMember", allEntries = true)
	public UserDomain resetFamilyPassword(String familyUserId, String password) {
		EntityManager em = null;
		UserDomain user = null;
		try {
			em = entityManagerFactory.createEntityManager();
			user = em.find(UserDomain.class, familyUserId);
			String now = DbUtils.getCurrentTimestamp();
			user.setUpdated(now);
			user.setPassword(password);

			em.persist(user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return user;
	}

	/**
	 * Update a user in SimpleDB
	 */
	@CacheEvict(value = "familyMember", allEntries = true)
	public UserDomain updateUser(String id, String password, String firstName,
			String lastName, String email, Date dob, boolean admin,
			GenderEnum gender, String roleName) {

		EntityManager em = null;
		UserDomain user = null;
		try {
			em = entityManagerFactory.createEntityManager();
			user = em.find(UserDomain.class, id);
			String now = DbUtils.getCurrentTimestamp();
			user.setUpdated(now);

			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setDob(DbUtils.encodeDate(dob));
			user.setAdmin(Boolean.valueOf(admin).toString());
			user.setGender(gender.name());
			user.setRoleName(roleName);
			em.persist(user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return user;

	}

	@Transactional
	@CacheEvict(value = "familyMember", allEntries = true)
	public UserDomain addHousehead(String familyName, String username,
			String email, String firstName, String lastName, String password,
			Date dob, String gender, String roleName, String street,
			String city, String state, String country, String zip,
			String  secureQuestionId, String  secureQuestionAnswer) {

		EntityManager em = null;

		UserDomain user = new UserDomain();

		try {
			em = entityManagerFactory.createEntityManager();

			user.setId(DbUtils.nextSequenceId());
			String now = DbUtils.getCurrentTimestamp();
			user.setUpdated(now);
			user.setCreated(now);

			user.setUsername(MyUtils.lowerCase(username));
			user.setEmail(email);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPassword(password);
			user.setDob(DbUtils.encodeDate(dob));
			user.setGender(gender);
			
			user.setSecureQuestionId(secureQuestionId);
			user.setSecureQuestionAnswer(secureQuestionAnswer);

			user.setRoleName(roleName);
			user.setAdmin(Boolean.TRUE.toString());
			user.setActive(Boolean.TRUE.toString());

			AddressDomain address = new AddressDomain();
			address.setId(DbUtils.nextSequenceId());
			address.setUpdated(now);
			address.setCreated(now);
			address.setStreet(street);
			address.setCity(city);
			address.setState(state);
			address.setCountry(country);
			address.setCountry(country);
			address.setZip(zip);

			FamilyDomain family = new FamilyDomain();
			family.setId(DbUtils.nextSequenceId());
			family.setUpdated(now);
			family.setCreated(now);

			family.setAddressId(address.getId());
			family.setHouseheadId(user.getId());
			family.setFamilyname(MyUtils.upperCase(familyName));

			em.persist(address);
			user.setFamilyId(family.getId());
			em.persist(user);
			em.persist(family);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return user;

	}

	@Transactional
	@CacheEvict(value = "familyMember", allEntries = true)
	public void addFamilyMember(String username, String email,
			String firstName, String lastName, String password, Date dob,
			String gender, String roleName, String familyId, String adminStr) {

		EntityManager em = null;

		UserDomain user = new UserDomain();

		try {
			em = entityManagerFactory.createEntityManager();

			user.setId(DbUtils.nextSequenceId());
			String now = DbUtils.getCurrentTimestamp();
			user.setUpdated(now);
			user.setCreated(now);

			user.setUsername(MyUtils.lowerCase(username));
			user.setEmail(email);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPassword(password);
			user.setDob(DbUtils.encodeDate(dob));
			user.setGender(gender);

			user.setRoleName(roleName);
			user.setAdmin(String.valueOf(MyUtils.isTrue(adminStr)));
			user.setActive(Boolean.TRUE.toString());

			user.setFamilyId(familyId);
			em.persist(user);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

	@CacheEvict(value = "familyMember", allEntries = true)
	public void activateUser(String username, boolean activate) {
		EntityManager em = null;

		try {
			em = entityManagerFactory.createEntityManager();
			UserDomain user = this.findUserByUsername(MyUtils
					.lowerCase(username));
			user.setActive(new Boolean(activate).toString());

			em.persist(user);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

	@CacheEvict(value = "familyMember", allEntries = true)
	public void deleteUser(String username) {
		EntityManager em = null;

		try {
			em = entityManagerFactory.createEntityManager();
			UserDomain user = this.findUserByUsername(MyUtils
					.lowerCase(username));

			em.remove(user);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

}
