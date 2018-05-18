package com.demo.integration.login.entity;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class User {
    
    private Integer id;
    private String username;
    private String name;
    private String password;
    private String salt;
    private byte state;
    private List<Role> roles;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public byte getState() {
        return state;
    }
    public void setState(byte state) {
        this.state = state;
    }
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    
}
