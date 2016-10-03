package com.family.web.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

@Configuration
@ComponentScan(basePackages = { "com.family.web" })
@PropertySource(value = { "classpath:application.properties" })
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	private static final String TILE_DEF_XML = "/WEB-INF/tiles/def/tiles-def.xml";

    @Autowired
    private Environment env;
    
	/**
	 * So that we can access static resources from web page
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(
				"/static/");
	}

	/**
	 * For the login page
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login.view");
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * View Resolver for the JSP page
	 * @return
	 */
	@Bean
	public ViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(1);
		return viewResolver;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tileConfigurerer = new TilesConfigurer();

		tileConfigurerer
				.setPreparerFactoryClass(SpringBeanPreparerFactory.class);

		tileConfigurerer.setDefinitions(TILE_DEF_XML);

		return tileConfigurerer;
	}

	/**
	 * View Resolver for the Tiles
	 * @return
	 */
	@Bean
	public ViewResolver tilesViewResolver() {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(TilesView.class);
		viewResolver.setOrder(0);
		return viewResolver;
	}
	
	//////////////////////
	// JSON configuration
	
	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(jsonMessageConverter);
		requestMappingHandlerAdapter.setMessageConverters(messageConverters);
		return requestMappingHandlerAdapter;
	}
	

	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String buildVersion = env.getRequiredProperty("buildVersion");
//    	System.out.println("############### buildVersion= " + buildVersion);
    	

		registry.addInterceptor(new MainInterceptor(buildVersion));
	}

	
	
}