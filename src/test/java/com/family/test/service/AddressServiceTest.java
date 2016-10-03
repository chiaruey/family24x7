package com.family.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.family.config.AppConfig;
import com.family.service.AddressService;
import com.family.service.bean.AddressBean;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { AppConfig.class})
public class AddressServiceTest {
	
	@Autowired
	private AddressService addressService;
	
	@Test
	public void testFindAddress() {
		long id = 150502073049842L;
		AddressBean address = null;
		
		try {		
			address = addressService.findAddress(id);
			System.out.println("testFindAddress> " + address);		
		} catch(Exception e) {
			e.printStackTrace();
		}	
		assertNotNull(address);		
	}
		

}
