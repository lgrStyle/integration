package com.demo.integration.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.integration.login.entity.Department;
import com.demo.integration.login.entity.User;
import com.demo.integration.login.service.ILoginService;


@Controller
public class LoginController {
    
    @Autowired
    private ILoginService loginService;
    
    @RequestMapping("/login")
    public String login(HttpServletRequest request,Map<String,Object> map) {
        String exception = (String) request.getAttribute("shiroLoginFailure");
        String msg = "";
        if(exception != null) {
            if(UnknownAccountException.class.getName().equals(exception)) {
                msg = "用户名不存在！";
            }else if(IncorrectCredentialsException.class.getName().equals(exception)) {
                msg = "密码不正确！";
            }else if(LockedAccountException.class.getName().equals(exception)) {
                msg = "账户已被锁定！";
            }else if("kaptchaValidateFailed".equals(exception)) {
                msg = "验证码不正确！";
            }else {
                msg = "其他错误："+exception;
            }
        }
        map.put("msg", msg);
        return "login";
    }
    
    @RequestMapping({"/","/index"})
    public String index(){
        return"index";
    }
    
    @RequestMapping("/403")
    public String unauthorizedRole(){
        return "403";
    }
    
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
    
    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userInfo")
    @RequiresPermissions("userInfo:view")//权限管理;
    public String userInfo(){
        return "userInfo";
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoAdd(User user){
        if(user.getUsername() != null) {
            loginService.addUser(user);
            return "userInfo";
        }
        return "userAdd";
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")//权限管理;
    public String userDel(){
        return "userDel";
    }
    
    @RequestMapping("save")
    @ResponseBody
    public Map<String,Object> save(Department department) {
        loginService.save(department);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", "200");
        map.put("msg", "保存成功");
        return map;
    }
    
    @RequestMapping("get/{id}")
    @ResponseBody
    public Map<String,Object> get(@PathVariable("id") Integer id) {
        Department department = loginService.getDepartmentById(id);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", "200");
        map.put("msg", "获取成功");
        map.put("data", department);
        return map;
    }
    
    @RequestMapping("update")
    @ResponseBody
    public Map<String,Object> update(Department department) {
        loginService.update(department);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", "200");
        map.put("msg", "修改成功");
        return map;
    }
    
    @RequestMapping("delete/{id}")
    @ResponseBody
    public Map<String,Object> delete(@PathVariable("id") Integer id) {
        loginService.delete(id);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", "200");
        map.put("msg", "删除成功");
        return map;
    }
}
