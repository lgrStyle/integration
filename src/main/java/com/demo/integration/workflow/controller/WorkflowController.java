package com.demo.integration.workflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.integration.workflow.entity.ActivityTypeEnum;

@Controller
public class WorkflowController {
    
    @Autowired
    private RepositoryService repositoryService;

    @SuppressWarnings("unused")
    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private ProcessEngine processEngine;
    
    @RequestMapping("test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String taskId = request.getParameter("taskId");
        
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
        
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivities, highLightedFlows);
        
        OutputStream outputStream = response.getOutputStream();
        
        byte[] b = new byte[1024];
        int len ;
        while((len = inputStream.read(b)) != -1) {
            outputStream.write(b,0,len);
        }
        outputStream.close();
        inputStream.close();
        
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
                
                // 遍历匹配的集合，取得开始时间最早的一个
                long earliestStamp = 0L;
                String flowId = null;
                for(Map<String,String> map : tempMapList) {
                    long startTime = Long.valueOf(map.get("startTime"));
                    if(earliestStamp == 0 || earliestStamp <= startTime) {
                        earliestStamp = startTime;
                        flowId = map.get("flowId");
                    }
                }
                highLightedFlows.add(flowId);
            }
        }
        
        return highLightedFlows;
    }
}
