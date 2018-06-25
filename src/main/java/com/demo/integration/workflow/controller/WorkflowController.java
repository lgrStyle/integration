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
import org.springframework.web.servlet.ModelAndView;

import com.demo.integration.workflow.service.WorkflowService;

@Controller
@RequestMapping("/workflow")
public class WorkflowController {
    
    @Autowired
    WorkflowService workflowService;
    
    @RequestMapping("/process-image")
    public String processImage() {
        return "process-image";
    }
    
    @RequestMapping("/main")
    public String main() {
        return "main";
    }
    
    @RequestMapping("/logging")
    @ResponseBody
    public String logging(){
        return "工作日志";
    }
    
    @RequestMapping("/schedule")
    @ResponseBody
    public String schedule(){
        return "日程安排";
    }
    
    @RequestMapping("/management")
    @ResponseBody
    public String management(){
        return "日程管理";
    }
    
    @RequestMapping("/mywork")
    @ResponseBody
    public String mywork(){
        return "我的工作";
    }
    
    @RequestMapping("/creatework")
    @ResponseBody
    public String creatework(){
        return "新建工作";
    }
    
    @RequestMapping("/history")
    @ResponseBody
    public String history(){
        return "历史查询";
    }
    
    @RequestMapping("/flowManage")
    @ResponseBody
    public String flowManage(){
        return "流程管理";
    }
    
    @RequestMapping("/getResourceByTaskId")
    public void getResourceByTaskId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
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
    
    @RequestMapping("/getResourceByKey")
    public void getResourceByKey(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
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
    
    @RequestMapping("/getResourceById")
    public void getResourceById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String processDefinitionId= request.getParameter("processDefinitionId");
        
        InputStream inputStream = workflowService.getResourceById(processDefinitionId);
        
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
    
    @RequestMapping("/processList")
    public ModelAndView processList() {
        Map<String,Object> map = workflowService.getDeployment();
        ModelAndView mav = new ModelAndView("/process-list",map);
        return mav;
    }
    
}
