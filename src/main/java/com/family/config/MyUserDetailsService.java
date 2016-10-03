package com.family.config;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.family.exception.UserNotActiveException;
import com.family.exception.UserNotFoundException;
import com.family.security.MyUserDetailsImpl;
import com.family.service.AccountService;
import com.family.service.bean.UserBean;

public class MyUserDetailsService implements UserDetailsService {

	private AccountService accountService;
	
	private Logger  logger = Logger.getLogger("com.family.config");
	
	public MyUserDetailsService(AccountService accountService){
		this.accountService = accountService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        UserBean user;
        
        if (username != null) {
        	user = accountService.findUserByUsername(username); 
        	logger.debug("loadUserByUsername> username = " + username + ", found User: " + user);
        } else {
        	throw new UsernameNotFoundException("User Name can not be null");
        }

        MyUserDetailsImpl userDetail = null;
        
        if (user != null && user.isActive()) {
        	userDetail = new MyUserDetailsImpl(user); 
        } else if (user == null) {
        	throw new UserNotFoundException("User " + username + " is Not Found");
        } else if (user.isActive() == false) {
        	throw new UserNotActiveException("User " + username + " is Not Active");
        }
         
        	
        return userDetail;
	}
	
}
