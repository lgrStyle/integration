package com.demo.integration.login.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.demo.integration.login.entity.Department;
import com.demo.integration.login.entity.User;


@Mapper
public interface LoginMapper {
    
    public void insert(Department department);
    
    public Department getById(Integer id);
    
    public void update(Department department);
    
    public void deleteById(Integer id);
    
    public User getUserInfo(String username);
    
    public void addUser(User user);
}
