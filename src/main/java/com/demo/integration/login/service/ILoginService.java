package com.demo.integration.login.service;

import com.demo.integration.login.entity.Department;
import com.demo.integration.login.entity.User;

public interface ILoginService {

    public Department save(Department department);
    
    public Department update(Department department);
    
    public Department getDepartmentById(Integer id);
    
    public void delete(Integer id);
    
    public User getUserInfo(String username);
}
