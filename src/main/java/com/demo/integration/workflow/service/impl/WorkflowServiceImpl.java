package com.demo.integration.workflow.service.impl;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.integration.login.entity.User;
import com.demo.integration.workflow.entity.ActivityTypeEnum;
import com.demo.integration.workflow.entity.Overtime;
import com.demo.integration.workflow.entity.WorkflowInfo;
import com.demo.integration.workflow.mapper.OvertimeMapper;
import com.demo.integration.workflow.mapper.WorkflowMapper;
import com.demo.integration.workflow.service.WorkflowService;

@Service
@Transactional
public class WorkflowServiceImpl implements WorkflowService{

    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private ProcessEngine processEngine;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private RuntimeService runtimeService;
    
    @SuppressWarnings("unused")
    @Autowired
    private ManagementService managementService;
    
    @SuppressWarnings("unused")
    @Autowired
    private FormService formService;
    
    @Autowired
    private WorkflowMapper workflowMapper;
    
    @Autowired
    private OvertimeMapper overtimeMapper;
    
    @Override
    public void delete(String deploymentId,boolean cascade) {
        repositoryService.deleteDeployment(deploymentId, cascade);
    }
    
    @Override
    public Map<String,Object> getDeployment() {
        Map<String,Object> map = new HashMap<String,Object>();
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().list();
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();
        map.put("deployment", deploymentList);
        map.put("processDefinition", processDefinitionList);
        return map;
    }
    
    @Override
    public void changeState(String state,String processDefinitionId,boolean cascade,Date date) {
        if("active".equals(state)) {
            repositoryService.activateProcessDefinitionById(processDefinitionId, cascade, date);
        }else if("suspend".equals(state)) {
            repositoryService.suspendProcessDefinitionById(processDefinitionId, cascade, date);
        }
    }
    
    @Override
    public InputStream getResourceByKey(String processDefinitionKey) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
        String processDefinitionId = processDefinition.getId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration  processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        
        ProcessDiagramGenerator processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", "宋体", "微软雅黑", "黑体", null, 1.0);
        
        return inputStream;
        
    }
    
    @Override
    public InputStream getResourceById(String processDefinitionId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration  processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        
        ProcessDiagramGenerator processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "jpg", "宋体", "微软雅黑", "黑体", null, 1.0);
        
        return inputStream;
    }
    
    @Override
    public InputStream getResourceByTaskId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        
        //获取高亮节点
        List<HistoricActivityInstance> highLightedActivityList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(task.getProcessInstanceId()).list();
        
        List<String> highLightedActivities = new ArrayList<>();
        
        for(HistoricActivityInstance activity : highLightedActivityList) {
            highLightedActivities.add(activity.getActivityId());
        }
        
        //获取高亮连线
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        
        List<String> highLightedFlows = getHighLightedFlows(bpmnModel,highLightedActivityList);
        
        //输出流程图
        ProcessEngineConfiguration  processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        
        ProcessDiagramGenerator processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivities, highLightedFlows,"宋体", "微软雅黑", "黑体", null, 1.0);
        
        return inputStream;
    }
    
    private List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> highLightedActivityList){
        //高亮线集合
        List<String> highLightedFlows = new ArrayList<>();
        
        //全部历史活动节点
        List<FlowNode> flowNodeList = new LinkedList<>();
        
        //已完成的历史活动实例
        List<HistoricActivityInstance> finishedActivityInstanceList = new LinkedList<>();
        
        for(HistoricActivityInstance historicActivityInstance : highLightedActivityList) {
            flowNodeList.add((FlowNode)bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId(),true));
            if(historicActivityInstance.getEndTime() != null) {
                finishedActivityInstanceList.add(historicActivityInstance);
            }
        }
        
        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        FlowNode currenFlowNode = null;
        for(HistoricActivityInstance currentActivityInstance : finishedActivityInstanceList) {
            currenFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId(),true);
            List<SequenceFlow> sequenceFlowList = currenFlowNode.getOutgoingFlows();
            
            /**
             * 遍历outgoingFlows并找到已已流转的
             * 满足如下条件认为已已流转：
             * 1.当前节点是并行网关或包含网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转
             * 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最近的流转节点视为有效流转
             */
            FlowNode targetFlowNode = null;
            if(ActivityTypeEnum.INCLUSIVE_GATEWAY.getType().equals(currentActivityInstance.getActivityType())
                    || ActivityTypeEnum.PARALLEL_GATEWAY.getType().equals(currentActivityInstance.getActivityType())) {
                
                // 遍历历史活动节点，找到匹配Flow目标节点的
                for(SequenceFlow sequenceFlow : sequenceFlowList) {
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef(),true);
                    if(flowNodeList.contains(targetFlowNode)) {
                        highLightedFlows.add(sequenceFlow.getId());
                    }
                }
            }else {
                List<Map<String, String>> tempMapList = new LinkedList<Map<String,String>>();
                Map<String, String> tempMap = null;
                for(SequenceFlow sequenceFlow : sequenceFlowList) {
                    for(HistoricActivityInstance historicActivityInstance : highLightedActivityList) {
                        tempMap = new HashMap<>();
                        if(historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                            tempMap.put("flowId", sequenceFlow.getId());
                            tempMap.put("startTime", String.valueOf(historicActivityInstance.getStartTime().getTime()));
                            tempMapList.add(tempMap);
                        }
                    }
                }
                
                // 遍历匹配的集合，取得开始时间最迟的一个
                long latestStamp = 0L;
                String flowId = null;
                for(Map<String,String> map : tempMapList) {
                    long startTime = Long.valueOf(map.get("startTime"));
                    if(latestStamp == 0 || latestStamp <= startTime) {
                        latestStamp = startTime;
                        flowId = map.get("flowId");
                    }
                }
                highLightedFlows.add(flowId);
            }
        }
        
        return highLightedFlows;
    }
    
    @Override
    public List<WorkflowInfo> myWaitList() throws SQLException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return workflowMapper.getMyWaitList(user);
    }
    
    @Override
    public List<WorkflowInfo> myDoneList() throws SQLException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return workflowMapper.getMyDoneList(user);
    }

    @Override
    public List<WorkflowInfo> historyList(WorkflowInfo workflowInfo) throws SQLException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return workflowMapper.getHistoryList(workflowInfo, user);
    }

    @Override
    @Transactional
    public void startFlow(WorkflowInfo workflowInfo, User user) throws Exception {
        String processKey = workflowInfo.getProcessKey();
        Map<String,Object> variables = new HashMap<String,Object>();
        variables.put("assignee", user.getUsername());
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processKey, variables);
        String businessId = "";
        switch(processKey) {
            case "testProcess":{
                businessId = insertOvertime(user);
                break;
            }
            case "leaveProcess":{
                businessId = insertOvertime(user);
                break;
            }
        }
        workflowInfo.setProcessInstanceId(pi.getProcessInstanceId());
        workflowInfo.setBusinessId(businessId);
        workflowInfo.setStatus("执行中");
        workflowInfo.setCreateBy(user.getUsername());
        workflowMapper.insertWorkflow(workflowInfo);
    }
    
    @Override
    public void completeTask(String processInstanceId, String taskId ,Map<String,Object> variables) throws SQLException {
        taskService.complete(taskId, variables);
        HistoricProcessInstance hpi =
        historyService.createHistoricProcessInstanceQuery()
            .processInstanceId(processInstanceId)
            .finished()
            .singleResult();
        if(hpi != null) {
            WorkflowInfo workflowInfo = new WorkflowInfo();
            workflowInfo.setProcessInstanceId(processInstanceId);
            workflowInfo.setStatus("已结束");
            workflowInfo.setCompletedTime(hpi.getEndTime());
            workflowMapper.updateWorkflow(workflowInfo);
        }
    }
    
    public String insertOvertime(User user) throws SQLException {
        Overtime overtime = new Overtime();
        overtime.setCreateBy(user.getUsername());
        overtimeMapper.insertData(overtime);
        return overtime.getId().toString();
    }

}
