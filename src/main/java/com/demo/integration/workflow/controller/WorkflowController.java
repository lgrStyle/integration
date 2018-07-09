package com.demo.integration.workflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.demo.integration.login.entity.User;
import com.demo.integration.workflow.service.WorkflowService;

@Controller
@RequestMapping("/workflow")
public class WorkflowController {
    
    @Autowired
    WorkflowService workflowService;
    
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
    public String mywork(){
        return "my-work";
    }
    
    @RequestMapping("/creatework")
    public String creatework(){
        return "new-work";
    }
    
    @RequestMapping("/history")
    public String history(){
        return "history-query";
    }
    
    @RequestMapping("/process-image")
    public ModelAndView processImage(String processDefinitionId, String processDefinitionKey, String taskId) {
        ModelAndView mav = new ModelAndView("process-image");
        if(processDefinitionId != null) {
            mav.addObject("processDefinitionId", processDefinitionId);
        }
        if(processDefinitionKey != null) {
            mav.addObject("processDefinitionKey", processDefinitionKey);
        }
        if(taskId != null) {
            mav.addObject("taskId", taskId);
        }
        return mav;
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
    @ResponseBody
    public String deleteDeploy(String deploymentId,boolean cascade) {
        workflowService.delete(deploymentId, cascade);
        return "success";
    }
    
    @RequestMapping("/processList")
    public ModelAndView processList() {
        Map<String,Object> map = workflowService.getDeployment();
        ModelAndView mav = new ModelAndView("/process-list",map);
        return mav;
    }
    
    @RequestMapping("/deploy")
    public String deploy(@RequestParam("deploymentFile") MultipartFile multipartFile, @RequestParam("deploymentName")String deploymentName) throws IOException{
        workflowService.deploy(multipartFile.getInputStream(), deploymentName);
        return "redirect:/workflow/processList";
    }
    
    @RequestMapping("/startFlow")
    @ResponseBody
    public String startFlow(HttpServletRequest request) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
//        String processKey = request.getParameter("processKey");
        String processName = request.getParameter("processName");
        String suffix = request.getParameter("suffix");
        String prefix = request.getParameter("prefix");
        String title = request.getParameter("title");
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        if(!"".equals(title)) {
            title = prefix+processName+"-"+user.getName()+"-"+sdf.format(new Date())+suffix;
        }
        
        workflowService.startFlow("", "", new HashMap<String,Object>());
        
        return "success";
    }
}
