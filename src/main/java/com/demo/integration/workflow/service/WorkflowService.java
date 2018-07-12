package com.demo.integration.workflow.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import com.demo.integration.workflow.entity.WorkflowInfo;


public interface WorkflowService {

    void delete(String deploymentId,boolean cascade);
    
    Map<String,Object> getDeployment();
    
    InputStream getResourceByTaskId(String taskId);

    InputStream getResourceByKey(String processDefinitionKey);
    
    InputStream getResourceById(String processDefinitionId);
    
    List<WorkflowInfo> myWaitList() throws SQLException;
}
