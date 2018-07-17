package com.demo.integration.workflow.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class WorkflowInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    private String taskId;
    private String executionId;
    private String processInstanceId;
    private String businessId;
    private String taskName;
    private String assignee;
    private String title;
    private String processKey;
    private String processName;
    private String createBy;
    private Date createTime;
    private Date claimTime;
    private Date receiptTime;
    private Date completedTime;
    private String status;
    private String attachment;
    private String operation;
    private String scope;
    
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getClaimTime() {
        return claimTime;
    }
    public void setClaimTime(Date claimTime) {
        this.claimTime = claimTime;
    }
    public String getBusinessId() {
        return businessId;
    }
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
    public String getCreateBy() {
        return createBy;
    }
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public Date getReceiptTime() {
        return receiptTime;
    }
    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }
    public Date getCompletedTime() {
        return completedTime;
    }
    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }
    public String getProcessKey() {
        return processKey;
    }
    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }
    public String getProcessName() {
        return processName;
    }
    public void setProcessName(String processName) {
        this.processName = processName;
    }
    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
    
}
