package com.family.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.spaceprogram.simplejpa.EntityManagerFactoryImpl;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"com.family.db"})
@PropertySource(value = { "classpath:application.properties" })
/**
 * @deprecated
 * @author Daddy
 */
public class SimpleDbConfig {
 
    @Autowired
    private Environment env;
    
    
	@Bean
	public PlatformTransactionManager transactionManager()
	{
		EntityManagerFactory factory = entityManagerFactory();
		return new JpaTransactionManager(factory);
	}

	@Bean
	   public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	      return new PropertySourcesPlaceholderConfigurer();
	   }
	
	@Bean
    public EntityManagerFactory entityManagerFactory() {
    	String persistenceUnitName = env.getRequiredProperty("sdb.persistenceUnitName");
    	String accessKey = env.getRequiredProperty("sdb.accessKey");
    	String secretKey = env.getRequiredProperty("sdb.secretKey");
    	String sdbEndpoint = env.getRequiredProperty("sdb.sdbEndpoint");
    	String printQueries = env.getRequiredProperty("sdb.printQueries");
    	String sessionless = env.getRequiredProperty("sdb.sessionless");

    	Set<String> classNameSet = this.getSdbClassNameSet();
    	
    	for (String className: classNameSet) {
    		System.out.println("className = " + className);
    	}
    	
    	Map<String, String> props = new HashMap<String, String>();
    	props.put("accessKey", accessKey);
    	props.put("secretKey", secretKey);
    	props.put("printQueries", printQueries);
    	props.put("sessionless", sessionless);
    	props.put("sdbEndpoint", sdbEndpoint);
    	
    	EntityManagerFactory entityManagerFactory = new EntityManagerFactoryImpl(persistenceUnitName, props, null, classNameSet);

        return entityManagerFactory;
    }
    
    private Set<String> getSdbClassNameSet() {
    	Set<String> classNameSet = new HashSet<String>();
    	boolean finished = false;
    	
    	int i = 1;
    	while (!finished) {
    		String className = "";
    		
    		try {
    			className = env.getRequiredProperty("sdb.className." + i);;
    		} catch(Exception e) {
    			System.out.println(e.getMessage());
    		}
    		
    		if (StringUtils.isBlank(className)) {
    			finished = true;
    		} else {
    			classNameSet.add(className);
    			i++;
    		}   		
    	}
    	return classNameSet;   	
    }

}