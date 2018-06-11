package com.demo.integration;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.After;
import org.junit.Before;
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
	public void contextLoads() throws FileNotFoundException {
	    long count = repositoryService.createDeploymentQuery()
	        .processDefinitionKey("testProcess")
	        .count();
	    assertEquals(count, 1);
	}
	
	public void addResource() {
	    InputStream bpmn = IntegrationApplicationTests.class.getClassLoader().getResourceAsStream("processes/testProcess.bpmn");
        InputStream png = IntegrationApplicationTests.class.getClassLoader().getResourceAsStream("processes/testProcess.png");
        repositoryService.createDeployment()
            .name("测试")
            .addInputStream("bpmn", bpmn)
            .addInputStream("png", png)
            .deploy();
	}
	
	@Before
	public void addZipResource() {
	    IntegrationApplicationTests.class.getClassLoader();
        InputStream in = ClassLoader.getSystemResourceAsStream("processes/test.zip");
	    ZipInputStream zip = new ZipInputStream(in);
	    repositoryService.createDeployment()
	        .name("zip测试")
	        .addZipInputStream(zip)
	        .deploy();
	}
	
	@After
	public void deleteResource() {
	    List<Deployment> list = repositoryService.createDeploymentQuery()
	            .processDefinitionKey("testProcess")
	            .list();
	    for(Deployment deployment : list) {
	        repositoryService.deleteDeployment(deployment.getId(), true);
	    }
	}
}
