package com.family.security;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.family.service.bean.UserBean;

public class MyUserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private UserBean user;
	
	private Collection<GrantedAuthority> authorities;
	
	public MyUserDetailsImpl(UserBean user) {
		this.user = user;	
						
		authorities = new ArrayList<GrantedAuthority>();
				
		// Currently, I only support one-to-one relationship between family and user		
		authorities.add(new SimpleGrantedAuthority ("ROLE_" + user.getRoleName()));
	}
		
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public boolean isEnabled() {
		return true;
	}	

	public String getPassword() {
		return user.getPassword();
	}

	public String getUsername() {
		return user.getEmail();
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	// Yet implemented methods
	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

}
