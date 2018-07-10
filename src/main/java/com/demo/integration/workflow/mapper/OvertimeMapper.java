package com.demo.integration.workflow.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.demo.integration.workflow.entity.Overtime;

@Mapper
public interface OvertimeMapper {
    
   void insertData(Overtime overtime) throws SQLException;
   
   void updateData(Overtime overtime) throws SQLException;
}
