package com.demo.integration.workflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WorkflowController {
    
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private ProcessEngine processEngine;
    
    @RequestMapping("test")
    public void test(HttpServletResponse response) throws IOException {
        
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("testProcess");
        
        //获取高亮节点
        List<HistoricActivityInstance> highLightedActivityList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).list();
        
        List<String> highLightedActivities = new ArrayList<>();
        
        for(HistoricActivityInstance activity : highLightedActivityList) {
            highLightedActivities.add(activity.getActivityId());
        }
        
//        //获取高亮连线
//        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
//        
//        List<String> highLightedFlows = getHighLightedFlows(processDefinitionEntity,highLightedActivityList);
        
        //输出流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        
        ProcessEngineConfiguration  processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        
        ProcessDiagramGenerator processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        
        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "jpg", highLightedActivities);
        
        byte[] b = new byte[1024];
        int len ;
        while((len = inputStream.read(b)) != -1) {
            response.getOutputStream().write(b,0,len);
        }
        
    }
    
//    private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity, List<HistoricActivityInstance> highLightedActivityList){
//        List<String> highLightedFlows = new ArrayList<>();
//        
//        return highLightedFlows;
//    }
}
