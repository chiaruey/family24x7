package com.family.db.dao;

import com.family.db.domain.AddressDomain;

public interface AddressDao {
	AddressDomain addAddress(String street, String city, String state,
			String country, String zip);

	AddressDomain updateAddress(String id, String street, String city,
			String state, String country, String zip);

	void removeAddress(String id);

	AddressDomain findAddress(String id);
}
