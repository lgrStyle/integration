package com.demo.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationApplicationTests {
    
    @Autowired
    private RepositoryService repositoryService;
    
	@Test
	public void contextLoads() {
	   ProcessDefinition processDefinition =  repositoryService.getProcessDefinition("test");
	   assertNull(processDefinition);
	}
	
	@Test
	public void notNullTest() {
	    assertNotNull(repositoryService);
	}

}
