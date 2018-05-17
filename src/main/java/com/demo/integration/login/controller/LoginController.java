package com.demo.integration.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.integration.login.entity.Department;
import com.demo.integration.login.service.ILoginService;


@RestController
public class LoginController {

    @Autowired
    private ILoginService loginService;
    
    @RequestMapping("save")
    public Map<String,Object> save(Department department) {
        loginService.save(department);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", "200");
        map.put("msg", "保存成功");
        return map;
    }
    
    @RequestMapping("get/{id}")
    public Map<String,Object> get(@PathVariable("id") Integer id) {
        Department department = loginService.getDepartmentById(id);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", "200");
        map.put("msg", "获取成功");
        map.put("data", department);
        return map;
    }
    
    @RequestMapping("update")
    public Map<String,Object> update(Department department) {
        loginService.update(department);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", "200");
        map.put("msg", "修改成功");
        return map;
    }
    
    @RequestMapping("delete/{id}")
    public Map<String,Object> delete(@PathVariable("id") Integer id) {
        loginService.delete(id);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", "200");
        map.put("msg", "删除成功");
        return map;
    }
}
