package com.family.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	
	public static MyUserDetailsImpl getUserDetails() {
		return (MyUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
	}

}
