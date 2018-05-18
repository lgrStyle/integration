package com.demo.integration.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.demo.integration.login.entity.Department;
import com.demo.integration.login.entity.User;
import com.demo.integration.login.mapper.LoginMapper;
import com.demo.integration.login.service.ILoginService;

@Service
@CacheConfig(cacheNames="loginService")
public class LoginService implements ILoginService{
    
    @Autowired
    private LoginMapper loginMapper;
    
    @CachePut(key="#department.id")
    public Department save(Department department){
        System.out.println("保存 id=" + department.getId() + " 的数据");
        loginMapper.insert(department);
        return department;
    }
    
    @CachePut(key="#department.id")
    public Department update(Department department) {
        System.out.println("修改 id=" + department.getId() + " 的数据");
        loginMapper.update(department);
        return department;
    } 
    
    @Cacheable(key="#id")
    public Department getDepartmentById(Integer id) {
        System.out.println("获取 id=" + id + " 的数据");
        Department department = loginMapper.getById(id);
        return department;
    }
    
    @CacheEvict(key="#id")
    public void delete(Integer id) {
        System.out.println("删除 id=" + id + " 的数据");
        loginMapper.deleteById(id);
    }

    @Override
    public User getUserInfo(String username) {
        
        return loginMapper.getUserInfo(username);
    } 
}
