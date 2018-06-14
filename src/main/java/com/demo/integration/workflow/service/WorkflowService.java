package com.demo.integration.workflow.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public interface WorkflowService {

    void deploy(File file,String name) throws FileNotFoundException;
    
    void delete(String deploymentId,boolean cascade);
    
    Map<String,Object> getDeployment();
    
    InputStream getResourceByTaskId(String taskId);

    InputStream getResourceByKey(String processDefinitionKey);
    
}
