package com.demo.integration.workflow.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.demo.integration.login.entity.User;
import com.demo.integration.workflow.entity.WorkflowInfo;

@Mapper
public interface WorkflowMapper {
    
    List<WorkflowInfo> getMyWaitList(User user) throws SQLException;
}
