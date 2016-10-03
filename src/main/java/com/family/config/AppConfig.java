package com.family.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
//import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages={"com.family"},
		excludeFilters=@ComponentScan.Filter(type=FilterType.REGEX, pattern={ "com.family.web.*"}))
//@EnableScheduling
@EnableAspectJAutoProxy
@EnableCaching
public class AppConfig {

	@Bean
	public CacheManager cacheManager()
	{
		return new ConcurrentMapCacheManager();
	}


}
