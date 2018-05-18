package com.demo.integration.login.entity;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Role {
    
    private Integer id;
    private String role;
    private String description;
    private Boolean avalible = Boolean.FALSE;
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
    public Boolean getAvalible() {
        return avalible;
    }
    public void setAvalible(Boolean avalible) {
        this.avalible = avalible;
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
