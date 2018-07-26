package com.demo.integration.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.integration.entity.Student;

public interface DemoDao extends JpaRepository<Student, Long>{
    
}
