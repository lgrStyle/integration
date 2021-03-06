package com.demo.integration.login.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Role implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String role;
    private String description;
    private Boolean available = Boolean.TRUE;
    private List<Permission> permissions;
    private List<User> users;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getAvailable() {
        return available;
    }
    public void setAvailable(Boolean available) {
        this.available = available;
    }
    public List<Permission> getPermissions() {
        return permissions;
    }
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    
}
