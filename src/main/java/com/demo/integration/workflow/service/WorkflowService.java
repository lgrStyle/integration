package com.demo.integration.workflow.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.demo.integration.login.entity.User;
import com.demo.integration.workflow.entity.WorkflowInfo;


public interface WorkflowService {

    void delete(String deploymentId,boolean cascade);
    
    Map<String,Object> getDeployment();
    
    InputStream getResourceByTaskId(String taskId);

    InputStream getResourceByKey(String processDefinitionKey);
    
    InputStream getResourceById(String processDefinitionId);
    
    List<WorkflowInfo> myWaitList() throws SQLException;
    
    List<WorkflowInfo> myDoneList() throws SQLException;
    
    List<WorkflowInfo> historyList(WorkflowInfo workflowInfo) throws SQLException;
    
    void startFlow(WorkflowInfo workflowInfo,User user) throws Exception;

    void changeState(String state, String processDefinitionId, boolean cascade, Date date);

    void completeTask(String processInstanceId, String taskId, Map<String,Object> variables) throws SQLException;
}
