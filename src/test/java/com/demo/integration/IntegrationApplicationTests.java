package com.demo.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.demo.integration.entity.Student;
import com.demo.integration.service.DemoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationApplicationTests {

    @Autowired
    private IdentityService identityService;
    
    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private FormService formService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private DemoService demoService;
    
    
    @Transactional
    public void allApproved() {
        String currentUser = "ming";
        identityService.setAuthenticatedUserId(currentUser);
        
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leaveProcess").singleResult();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,String> variables = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        String startDate = sdf.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        String endDate = sdf.format(calendar.getTime());
        variables.put("startDate", startDate);
        variables.put("endDate", endDate);
        variables.put("reason", "调休");
        
        ProcessInstance pi = formService.submitStartFormData(pd.getId(), variables);
        assertNotNull(pi);
        
        Task deptTask = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
        variables = new HashMap<>();
        variables.put("deptLeaderApprove", "true");
        formService.submitTaskFormData(deptTask.getId(), variables);
        
        Task hrTask = taskService.createTaskQuery().taskCandidateGroup("hr").singleResult();
        variables = new HashMap<>();
        variables.put("hrApprove", "true");
        formService.submitTaskFormData(hrTask.getId(), variables);
        
        Task reportBackTask = taskService.createTaskQuery().taskAssignee(currentUser).singleResult();
        variables = new HashMap<>();
        variables.put("reportBackDate", "2018-07-20");
        formService.submitTaskFormData(reportBackTask.getId(), variables);
        
        HistoricProcessInstance hpi =  historyService.createHistoricProcessInstanceQuery().finished().singleResult();
        assertNotNull(hpi);
        
        Map<String,Object> historyVariables = packageVariables(pi);
        assertEquals("ok", historyVariables.get("result"));
    }
    
    private Map<String,Object> packageVariables(ProcessInstance pi){
        Map<String,Object> variables = new HashMap<>();
        List<HistoricDetail> list = historyService.createHistoricDetailQuery().processInstanceId(pi.getProcessInstanceId()).list();
        List<HistoricVariableInstance> varList = historyService.createHistoricVariableInstanceQuery().processInstanceId(pi.getProcessInstanceId()).list();
        for(HistoricVariableInstance var : varList) {
            variables.put(var.getVariableName(),var.getValue());
            System.out.println("variable: "+var.getVariableName()+"="+var.getValue());
        }
        for(HistoricDetail historicDetail : list) {
            if(historicDetail instanceof HistoricFormProperty) {
                HistoricFormProperty field = (HistoricFormProperty) historicDetail;
                variables.put(field.getPropertyId(), field.getPropertyValue());
                System.out.println("form field: taskId="+field.getTaskId()+","+field.getPropertyId()+"="+field.getPropertyValue());
            }
        }
        return variables;
    }
    
//    @Test
    public void jpaDemo() {
        Student s = new Student();
        s.setName("lgr");
        demoService.save(s);
        System.out.println("已完成");
    }
    
    @Test
    public void demo2() {
        Student s = demoService.findById(3);
        System.out.println(s.getName());
        s.setName("333");
    }
}
