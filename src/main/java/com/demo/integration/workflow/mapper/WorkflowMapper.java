package com.demo.integration.workflow.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.demo.integration.login.entity.User;
import com.demo.integration.workflow.entity.WorkflowInfo;

@Mapper
public interface WorkflowMapper {
    
    List<WorkflowInfo> getMyWaitList(User user) throws SQLException;
    
    List<WorkflowInfo> getMyDoneList(User user) throws SQLException;
    
    List<WorkflowInfo> getHistoryList(@Param("workflowInfo") WorkflowInfo workflowInfo, @Param("user")User user) throws SQLException;
    
    void insertWorkflow(WorkflowInfo workflowInfo) throws SQLException;
    
    void updateWorkflow(WorkflowInfo workflowInfo) throws SQLException;
}
