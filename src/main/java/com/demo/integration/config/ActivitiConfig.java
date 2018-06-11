package com.demo.integration.config;

import javax.sql.DataSource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class ActivitiConfig {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiConfig.class);
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private DataSourceTransactionManager transactionManager;
    
    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration() {
        logger.info("-----activiti配置-----");
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);
        processEngineConfiguration.setTransactionManager(transactionManager);
        processEngineConfiguration.setDatabaseSchemaUpdate("true");
        return processEngineConfiguration;
    }
    
    @Bean
    public ProcessEngine processEngine() throws Exception {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration());
        ProcessEngine processEngine = processEngineFactoryBean.getObject();
        return processEngine;
    }
    
    @Bean
    public RepositoryService repositoryService() throws Exception {
        RepositoryService repositoryService = processEngine().getRepositoryService();
        return repositoryService;
    }
    
    @Bean
    public RuntimeService runtimeService() throws Exception {
        RuntimeService runtimeService = processEngine().getRuntimeService();
        return runtimeService;
    }
    
    @Bean 
    public TaskService taskService() throws Exception {
        TaskService taskService = processEngine().getTaskService();
        return taskService;
    }
    
    @Bean
    public HistoryService historyService() throws Exception {
        HistoryService historyService = processEngine().getHistoryService();
        return historyService;
    }
    
    @Bean
    public FormService formService() throws Exception {
        FormService formService = processEngine().getFormService();
        return formService;
    }
    
    @Bean 
    public ManagementService managementService() throws Exception {
        ManagementService managementService = processEngine().getManagementService();
        return managementService;
    }
    
    @Bean
    public IdentityService identityService() throws Exception {
        IdentityService identityService = processEngine().getIdentityService();
        return identityService;
    }
}
