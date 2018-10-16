package com.demo.integration;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableCaching
@EnableTransactionManagement
public class IntegrationApplication extends SpringBootServletInitializer{
    
    private static Logger logger = LoggerFactory.getLogger(IntegrationApplication.class);
    
	public static void main(String[] args) {
	    logger.info("jar方式启动！");
		SpringApplication.run(IntegrationApplication.class, args);
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        logger.info("war方式启动！");
        return builder.sources(IntegrationApplication.class);
    }
	
	
}
