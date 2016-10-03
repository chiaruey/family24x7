package com.family.web.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class MyServletContextListener implements ServletContextListener {
	private Logger  logger = Logger.getLogger("com.family.web.controller");
	
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {

	}

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {

	    try {
	        com.amazonaws.http.IdleConnectionReaper.shutdown();
	    } catch (Throwable t) {
	        logger.error("fail to destroy Context", t);
	    }
	}
}
