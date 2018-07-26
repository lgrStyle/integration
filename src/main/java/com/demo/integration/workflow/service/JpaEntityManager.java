package com.demo.integration.workflow.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.integration.workflow.entity.JpaEntity;

@Service
public class JpaEntityManager {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    public JpaEntity newEntity(DelegateExecution execution) {
        JpaEntity jpaEntity = new JpaEntity();
        jpaEntity.setProcessInstanceId(execution.getProcessInstanceId());
        jpaEntity.setUserId(execution.getVariable("userId").toString());
        jpaEntity.setStartTime(new Date());
        jpaEntity.setEndTime(new Date());
        entityManager.persist(jpaEntity);
        return jpaEntity;
    }
}
