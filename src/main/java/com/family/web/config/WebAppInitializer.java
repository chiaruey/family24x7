package com.family.web.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.family.config.AppConfig;

public class WebAppInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		
		super.onStartup(servletContext);
		servletContext.addListener(new MyServletContextListener());

	}

	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { AppConfig.class };
	}

	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}

	protected String[] getServletMappings() {
		return new String[] { "/", "*.json" };
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {
				new DelegatingFilterProxy("springSecurityFilterChain"),
				new OpenEntityManagerInViewFilter() };
	}

}
