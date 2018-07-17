package com.demo.integration.workflow.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.integration.workflow.entity.Overtime;
import com.demo.integration.workflow.mapper.OvertimeMapper;
import com.demo.integration.workflow.service.OvertimeService;

@Service
public class OvertimeServiceImpl implements OvertimeService{

    @Autowired
    private OvertimeMapper overtimeMapper;
    
    
    @Override
    public void insertData(Overtime overtime) throws SQLException {
        overtimeMapper.insertData(overtime);
    }

    @Override
    public void updateData(Overtime overtime) throws SQLException {
        overtimeMapper.updateData(overtime);
    }

}
