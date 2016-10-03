package com.family.test.db.dao.simplejpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.family.config.SimpleDbConfig;
import com.family.db.dao.AddressDao;
import com.family.db.domain.AddressDomain;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {
	SimpleDbConfig.class})
public class AddressSimplejpaDaoTest {

	@Autowired
	private AddressDao addressDao;

	@Test
	public void testFindAddress() {

		AddressDomain address = null;
		try {
			String id = "150502175901125";
			address = addressDao.findAddress(id);
			
			System.out.println("address = " + address);

		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(address);
	}
	
}
