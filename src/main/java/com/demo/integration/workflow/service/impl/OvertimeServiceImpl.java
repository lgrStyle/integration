package com.demo.integration.workflow.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.integration.login.entity.User;
import com.demo.integration.workflow.entity.Overtime;
import com.demo.integration.workflow.mapper.OvertimeMapper;
import com.demo.integration.workflow.service.OvertimeService;

@Service
public class OvertimeServiceImpl implements OvertimeService{

    @Autowired
    private OvertimeMapper overtimeMapper;
    
    @Autowired
    private RuntimeService runtimeService;
    
    @Override
    public void insertData(Overtime overtime) throws SQLException {
        overtimeMapper.insertData(overtime);
    }

    @Override
    public void updateData(Overtime overtime) throws SQLException {
        overtimeMapper.updateData(overtime);
    }
    
    @Override
    @Transactional
    public void startFlow(String processKey, String title, User user) throws Exception {
        Overtime overtime = new Overtime();
        overtime.setTitle(title);
        overtime.setStatus("进行中");
        overtime.setUsername(user.getUsername());
        overtimeMapper.insertData(overtime);
        
        Map<String,Object> variables = new HashMap<String,Object>();
        variables.put("assignee", user.getUsername());
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processKey, processKey+":"+overtime.getId(), variables);
        
        overtime.setFlowId(pi.getProcessInstanceId());
        overtimeMapper.updateData(overtime);
        
    }

}
