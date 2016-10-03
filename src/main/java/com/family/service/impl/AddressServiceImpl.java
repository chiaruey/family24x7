package com.family.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.family.db.dao.AddressDao;
import com.family.db.domain.AddressDomain;
import com.family.service.AddressService;
import com.family.service.bean.AddressBean;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao addressDao;
	
	public AddressBean addAddress(String street, String city, String state, String country, String zip) {
		AddressDomain domain = addressDao.addAddress(street, city, state, country, zip);
		
		return new AddressBean(domain);
	}

	public void updateAddress(long id, String street, String city, String state,
			String country, String zip) {
		addressDao.updateAddress(String.valueOf(id), street, city, state, country, zip);
	}
	
	public AddressBean findAddress(long id) {
		return new AddressBean(addressDao.findAddress(String.valueOf(id)));
	}
}
