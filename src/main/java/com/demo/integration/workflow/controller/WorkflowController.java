package com.demo.integration.workflow.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    private TaskService taskService;
    
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
    
    @RequestMapping("/processManage")
    public String processManage() {
        return "process-manage";
    }
    
    @RequestMapping("/processJob")
    public String processJob(){
        return "process-job";
    }
    
    @RequestMapping("/forms/leaveForm")
    public String leaveForm() {
        return "/forms/leave-form";
    }
    
    @RequestMapping("/forms/testForm")
    public String testForm() {
        return "/forms/test-form";
    }
    
    @RequestMapping("/processForm")
    public ModelAndView processForm(String processInstanceId, String taskId, String processKey) {
        ModelAndView mav = new ModelAndView("process-form");
        mav.addObject("processInstanceId", processInstanceId);
        mav.addObject("taskId", taskId);
        mav.addObject("processKey", processKey);
        return mav;
    }
    
    @RequestMapping("/processPrint")
    public ModelAndView processPrint(String processInstanceId, String processKey) {
        ModelAndView mav = new ModelAndView("process-print");
        mav.addObject("processInstanceId", processInstanceId);
        mav.addObject("processKey", processKey);
        return mav;
    }
    
    
    @RequestMapping("/processImage")
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
    
    @RequestMapping("/changeState/{state}")
    public String changeState(@PathVariable("state")String state,
            @RequestParam("processDefinitionId") String processDefinitionId,
            @RequestParam(value = "cascade", required =false) boolean cascade,
            @RequestParam(value = "date", required =false) Date date) {
        workflowService.changeState(state, processDefinitionId, cascade, date);
        return "redirect:/workflow/processList";
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
    
    @RequestMapping("/deleteTask")
    public String deleteTask(String taskId) {
        taskService.deleteTask(taskId, true);
        return "redirect:/workflow/myWaitList";
    }
    
    @RequestMapping("/upload/attachment")
    @ResponseBody
    public String newAttachment(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws IOException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        identityService.setAuthenticatedUserId(user.getUsername());
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        String attachmentType = file.getContentType() + ";" + extension;
        String taskId = request.getParameter("taskId");
        String processInstanceId = request.getParameter("processInstanceId");
        String attachmentName = request.getParameter("attachmentName");
        String attachmentDescription = request.getParameter("attachmentDescription");
        taskService.createAttachment(attachmentType, taskId, processInstanceId, attachmentName, attachmentDescription, file.getInputStream());
        return "success";
    }
    
    @RequestMapping("/download/{attachmentId}")
    public void getAttachment(@PathVariable("attachment") String attachmentId, HttpServletResponse response) throws IOException {
        //查询附件对象
        Attachment attachment = taskService.getAttachment(attachmentId);
        //读取附件二进制流
        InputStream attachmentContent = taskService.getAttachmentContent(attachmentId);
        String contentType = StringUtils.substringBefore(attachment.getType(), ";") ;
        response.addHeader("Content-Type", contentType + ";charset=UTF-8");
        
        String extension = StringUtils.substringAfter(attachment.getType(), ";");
        String fileName = attachment.getName() + "." + extension;
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        
        IOUtils.copy(new BufferedInputStream(attachmentContent), response.getOutputStream());
    } 
    
    @RequestMapping("/delete/{attachmentId}")
    @ResponseBody
    public String delAttachent(@PathVariable("attachment") String attachmentId) {
        taskService.deleteAttachment(attachmentId);
        return "success";
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
        WorkflowInfo workflowInfo = new WorkflowInfo();
        workflowInfo.setProcessKey(processKey);
        workflowInfo.setProcessName(processName);
        workflowInfo.setTitle(title);
        try {
            workflowService.startFlow(workflowInfo, user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return "success";
    }
    
    @RequestMapping("/testTask")
    @ResponseBody
    public ResponseData testTask(@RequestParam Map<String,String> map) {
        String processInstanceId = map.get("processInstanceId");
        String taskId = map.get("taskId");
        ResponseData responseData = new ResponseData();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String,Object> variables = new HashMap<String,Object>();
        if("并行任务2".equals(task.getName())) {
            variables.put("condition", "继续");
        }
        variables.put("assignee", user.getUsername());
        try {
            workflowService.completeTask(processInstanceId, taskId ,variables);
        } catch (Exception e) {
            responseData.setMessage(e.getMessage());
            responseData.setState("error");
            logger.error(e.getMessage());
        }
        return responseData;
    }
    
    @RequestMapping("/leaveTask")
    @ResponseBody
    public ResponseData leaveTask(@RequestParam Map<String,String> map) {
        String processInstanceId = map.get("processInstanceId");
        String taskId = map.get("taskId");
        ResponseData responseData = new ResponseData();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String,Object> variables = new HashMap<String,Object>();
        if("部门领导审批".equals(task.getName())) {
            variables.put("deptLeaderApprove", "true");
            variables.put("assignee", user.getUsername());
        }else if("人事审批".equals(task.getName())) {
            variables.put("hrApprove", "true");
        }
        try {
            workflowService.completeTask(processInstanceId, taskId,variables);
        } catch (Exception e) {
            responseData.setMessage(e.getMessage());
            responseData.setState("error");
            logger.error(e.getMessage());
        }
        return responseData;
    }
    
    @RequestMapping("/myWaitList")
    @ResponseBody
    public ResponseData myWaitList(WorkflowInfo workflowInfo) throws SQLException {
        List<WorkflowInfo> list = workflowService.myWaitList();
        ResponseData responseData = new ResponseData();
        responseData.setTotal(list.size());
        responseData.setRows(list);
        return responseData;
    }
    
    @RequestMapping("/myDoneList")
    @ResponseBody
    public Object myDoneList(WorkflowInfo workflowInfo) throws SQLException{
        List<WorkflowInfo> list = workflowService.myDoneList();
        ResponseData responseData = new ResponseData();
        responseData.setTotal(list.size());
        responseData.setRows(list);
        return responseData;
    }
    
    @RequestMapping("/historyList")
    @ResponseBody
    public Object historyList(WorkflowInfo workflowInfo) throws SQLException{
        List<WorkflowInfo> list = workflowService.historyList(workflowInfo);
        ResponseData responseData = new ResponseData();
        responseData.setTotal(list.size());
        responseData.setRows(list);
        return responseData;
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
