package com.family.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MainInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = Logger.getLogger("com.family.web.controller");

	public static final String BUILD_VERSION = "buildVersion";
	public static final String APP_PATH = "appPath";

	private String buildVersion;

	public MainInterceptor(String buildVersion) {
		this.buildVersion = buildVersion;
	}

	/**
	 * This implementation always returns {@code true}. 
	 * request URI =/ROOT/login 
	 * ContextPath = /ROOT 
	 * Local Name = 127.0.0.1
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		logger.error("request URI = " + request.getRequestURI());
//		logger.error("ContextPath = " + request.getContextPath());
//		logger.error("Local Name = " + request.getLocalName());
		return true;
	}

	/**
	 * request URI = /login 
	 * ContextPath = 
	 * Local Name = 127.0.0.1
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			final ModelAndView modelAndView) throws Exception {
//		logger.error("request URI = " + request.getRequestURI());
//		logger.error("ContextPath = " + request.getContextPath());
//		logger.error("Local Name = " + request.getLocalName());
		
		String appPath = request.getContextPath() + "/";
		logger.debug("appPath = " + appPath);
		request.setAttribute(BUILD_VERSION, buildVersion);
		request.setAttribute(APP_PATH,  appPath);
		
	}

}
