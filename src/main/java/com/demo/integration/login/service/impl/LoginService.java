package com.demo.integration.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.integration.login.entity.Department;
import com.demo.integration.login.mapper.LoginMapper;
import com.demo.integration.login.service.ILoginService;

@Service
public class LoginService implements ILoginService{
    
    @Autowired
    private LoginMapper loginMapper;
    
    public Department save(Department department){
        System.out.println("保存 id=" + department.getId() + " 的数据");
        loginMapper.insert(department);
        return department;
    }
    
    public Department update(Department department) {
        System.out.println("修改 id=" + department.getId() + " 的数据");
        loginMapper.update(department);
        return department;
    } 
    
    public Department getDepartmentById(Integer id) {
        System.out.println("获取 id=" + id + " 的数据");
        Department department = loginMapper.getById(id);
        return department;
    }
    
    public void delete(Integer id) {
        System.out.println("删除 id=" + id + " 的数据");
        loginMapper.deleteById(id);
    } 
}
