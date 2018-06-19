package com.demo.integration.workflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.integration.workflow.service.WorkflowService;

@Controller
public class WorkflowController {
    
    @Autowired
    WorkflowService workflowService;
    
    @RequestMapping("/processImage")
    public String image() {
        return "processImage";
    }
    
    @RequestMapping("/main")
    public String main() {
        return "main";
    }
    
    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String taskId = request.getParameter("taskId");
        
        InputStream inputStream = workflowService.getResourceByTaskId(taskId);
        
        OutputStream outputStream = response.getOutputStream();
        
        byte[] b = new byte[1024];
        int len ;
        while((len = inputStream.read(b)) != -1) {
            outputStream.write(b,0,len);
        }
        outputStream.close();
        inputStream.close();
        
    }
    
    @RequestMapping("/test1")
    public void test1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String processDefinitionKey = request.getParameter("processDefinitionKey");
        
        InputStream inputStream = workflowService.getResourceByKey(processDefinitionKey);
        
        OutputStream outputStream = response.getOutputStream();
        
        byte[] b = new byte[1024];
        int len ;
        while((len = inputStream.read(b)) != -1) {
            outputStream.write(b,0,len);
        }
        outputStream.close();
        inputStream.close();
        
    }
    
    @RequestMapping("/deleteDeploy")
    public void deleteDeploy(String deploymentId,boolean cascade) {
        workflowService.delete(deploymentId, cascade);
    }
    
    @RequestMapping("/getDeployment")
    @ResponseBody
    public Map<String,Object> getDeployment(){
        return workflowService.getDeployment();
    }
    
    
}
