package com.demo.integration.workflow.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "JPA_TEST")
public class JpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String processInstanceId;
    private String userId;
    
    @Temporal(TemporalType.DATE)
    private Date startTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    private String approve;
    private String comment;
    
    public JpaEntity() {
        
    }
    
    public JpaEntity(long id, String processInstanceId, String userId, Date startTime, Date endTime, String approve,
            String comment) {
        this.id = id;
        this.processInstanceId = processInstanceId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.approve = approve;
        this.comment = comment;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getProcessInstanceId() {
        return processInstanceId;
    }
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public String getApprove() {
        return approve;
    }
    public void setApprove(String approve) {
        this.approve = approve;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    @Override
    public String toString() {
        return "jpaEntity [id=" + id + ", processInstanceId=" + processInstanceId + ", userId=" + userId
                + ", startTime=" + startTime + ", endTime=" + endTime + ", approve=" + approve + ", comment=" + comment
                + "]";
    }
    
    
    
}
