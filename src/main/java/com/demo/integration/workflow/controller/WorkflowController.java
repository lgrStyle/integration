package com.demo.integration.workflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.integration.workflow.service.WorkflowService;

@Controller
@RequestMapping("/workflow")
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
    
    @RequestMapping("/logging")
    @ResponseBody
    public Map<String,String> logging(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("1", "工作日志");
        return map;
    }
    
    @RequestMapping("/schedule")
    @ResponseBody
    public Map<String,String> schedule(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("1", "日程安排");
        return map;
    }
    
    @RequestMapping("/management")
    @ResponseBody
    public Map<String,String> management(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("1", "日程管理");
        return map;
    }
    
    @RequestMapping("/mywork")
    @ResponseBody
    public Map<String,String> mywork(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("1", "我的工作");
        return map;
    }
    
    @RequestMapping("/creatework")
    @ResponseBody
    public Map<String,String> creatework(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("1", "新建工作");
        return map;
    }
    
    @RequestMapping("/history")
    @ResponseBody
    public String history(){
        return "历史查询";
    }
    
    
}
