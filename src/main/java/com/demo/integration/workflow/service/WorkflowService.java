package com.demo.integration.workflow.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public interface WorkflowService {

    void deploy(InputStream in,String name) throws FileNotFoundException;
    
    void delete(String deploymentId,boolean cascade);
    
    Map<String,Object> getDeployment();
    
    InputStream getResourceByTaskId(String taskId);

    InputStream getResourceByKey(String processDefinitionKey);
    
    InputStream getResourceById(String processDefinitionId);
    
}
