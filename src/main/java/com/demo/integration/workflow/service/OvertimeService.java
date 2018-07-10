package com.demo.integration.workflow.service;

import java.sql.SQLException;

import com.demo.integration.login.entity.User;
import com.demo.integration.workflow.entity.Overtime;

public interface OvertimeService {
    
    void insertData(Overtime overtime) throws SQLException;
    
    void updateData(Overtime overtime) throws SQLException;
    
    void startFlow(String processKey, String title, User user) throws Exception;
}
