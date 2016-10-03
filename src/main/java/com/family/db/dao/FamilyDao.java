package com.family.db.dao;

import com.family.db.domain.FamilyDomain;

public interface FamilyDao {
	
	FamilyDomain addFamily(String househeadId, String addressId);
	
	FamilyDomain findFamilyByFamilyname(String familyName);
	
	FamilyDomain findFamilyById(String familyId);

}
