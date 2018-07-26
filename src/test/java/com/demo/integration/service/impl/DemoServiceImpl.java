package com.demo.integration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.integration.dao.DemoDao;
import com.demo.integration.entity.Student;
import com.demo.integration.service.DemoService;

@Service
public class DemoServiceImpl implements DemoService{

    @Autowired
    private DemoDao demoDao;
    
    @Override
    public void save(Student s) {
        demoDao.save(s);
        
    }

    @Override
    public Student findById(long id) {
        return demoDao.findById(id).get();
    }

}
