package com.family.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import com.family.security.AjaxAuthenticationSuccessHandler;
import com.family.service.AccountService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private AccountService accountService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        
		http
		.csrf().disable()
			.authorizeRequests()
				.antMatchers("/resources/**","/static/**","/registration/**","/accessHelp/**","/error/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.defaultSuccessUrl("/home")				
				.loginProcessingUrl("/authenticate")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(new AjaxAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler()))
				.loginPage("/login")
//				.failureUrl("/login")
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.permitAll();
	}
	
	
	// @formatter:on

	protected void configure(AuthenticationManagerBuilder registry) throws Exception {
		MyUserDetailsService userDetailService = new MyUserDetailsService(accountService);
		registry.userDetailsService(userDetailService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	

}
