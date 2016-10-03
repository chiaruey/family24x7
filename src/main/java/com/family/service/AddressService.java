package com.family.service;

import com.family.service.bean.AddressBean;

public interface AddressService {

	AddressBean addAddress(String street, String city, String state, String country, String zip); 
	
	void updateAddress(long id, String street, String city, String state, String country, String zip);

	AddressBean findAddress(long id);
}
