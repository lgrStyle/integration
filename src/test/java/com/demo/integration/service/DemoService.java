package com.demo.integration.service;

import com.demo.integration.entity.Student;

public interface DemoService {

    void save(Student s);
    
    Student findById(long id);
}
