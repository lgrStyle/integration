package com.demo.integration.workflow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.demo.integration.comom.entity.ResponseData;
import com.demo.integration.login.entity.User;
import com.demo.integration.workflow.entity.WorkflowInfo;
import com.demo.integration.workflow.service.OvertimeService;
import com.demo.integration.workflow.service.WorkflowService;

@Controller
@RequestMapping("/workflow")
public class WorkflowController {
    
    private static final Logger logger = LoggerFactory.getLogger(WorkflowController.class);
    
    @Autowired
    WorkflowService workflowService;
    
    @Autowired
    OvertimeService overtimeService;
    
    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private IdentityService identityService;
    
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
    
    @RequestMapping("/myWork")
    public String myWork(){
        return "my-work";
    }
    
    @RequestMapping("/myWait")
    public String myWait(){
        return "my-wait";
    }
    
    @RequestMapping("/myDone")
    public String myDone(){
        return "my-done";
    }
    
    @RequestMapping("/myAttention")
    public String myAttention(){
        return "my-attention";
    }
    
    @RequestMapping("/myLookup")
    public String myLookup(){
        return "my-lookup";
    }
    
    @RequestMapping("/myHang")
    public String myHang(){
        return "my-hang";
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
    public String deploy(@RequestParam("deploymentFile") MultipartFile file, @RequestParam("deploymentName")String deploymentName) throws IOException{
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
        if("zip".equals(extension) || "bar".equals(extension)) {
            ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
            repositoryService.createDeployment()
                .name(deploymentName)
                .addZipInputStream(zipInputStream)
                .deploy();
        }else{
            repositoryService.createDeployment()
                .name(deploymentName)
                .addInputStream(fileName, file.getInputStream())
                .deploy();
        }
        return "redirect:/workflow/processList";
    }
    
    @RequestMapping("/startFlow")
    @ResponseBody
    public String startFlow(HttpServletRequest request) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        identityService.setAuthenticatedUserId(user.getUsername());
        String processKey = request.getParameter("processKey");
        String processName = request.getParameter("processName");
        String suffix = request.getParameter("suffix");
        String prefix = request.getParameter("prefix");
        String title = request.getParameter("title");
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        if("".equals(title)) {
            title = prefix+processName+"-"+user.getName()+"-"+sdf.format(new Date())+suffix;
        }
        try {
            switch(processKey) {
            case "testProcess":{
                overtimeService.startFlow(processKey, title, user);
                break;
            }
            case "leaveProcess" : {
                overtimeService.startFlow(processKey, title, user);
                break;
            }
        
        }
        }catch(Exception e) {
            logger.error(e.toString());
            return e.getMessage();
        }
        return "success";
    }
    
    @RequestMapping("/myWaitList")
    @ResponseBody
    public Object myWaitList() throws SQLException {
        List<WorkflowInfo> list = workflowService.myWaitList();
        ResponseData responseData = new ResponseData();
        responseData.setTotal(list.size());
        responseData.setRows(list);
        return responseData;
    }
}
