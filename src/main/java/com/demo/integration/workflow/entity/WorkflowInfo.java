package com.demo.integration.workflow.entity;

import java.io.Serializable;
import java.util.Date;

public class WorkflowInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    private String taskId;
    private String executionId;
    private String processInstanceId;
    private String taskName;
    private Date CreateTime;
    private Date ClaimTime;
    private String assignee ;
    private String id;
    private String title;
    private String processType;
    private String originator;
    private String receiptTime;
    private String completedTime;
    private String status;
    private String attachment;
    private String operation;
    
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getExecutionId() {
        return executionId;
    }
    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }
    public String getProcessInstanceId() {
        return processInstanceId;
    }
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public Date getCreateTime() {
        return CreateTime;
    }
    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }
    public Date getClaimTime() {
        return ClaimTime;
    }
    public void setClaimTime(Date claimTime) {
        ClaimTime = claimTime;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getProcessType() {
        return processType;
    }
    public void setProcessType(String processType) {
        this.processType = processType;
    }
    public String getOriginator() {
        return originator;
    }
    public void setOriginator(String originator) {
        this.originator = originator;
    }
    public String getReceiptTime() {
        return receiptTime;
    }
    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }
    public String getCompletedTime() {
        return completedTime;
    }
    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getAttachment() {
        return attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getAssignee() {
        return assignee;
    }
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
    
}
