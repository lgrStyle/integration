package com.demo.integration.login.entity;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Permission {
    private Integer id;
    private String name;
    private String resourceType;
    private String url;
    private String permission;
    private Long parrentId;
    private String parrentIds;
    private Boolean avalible = Boolean.FALSE;
    private List<Role> roles;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getResourceType() {
        return resourceType;
    }
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
    public Long getParrentId() {
        return parrentId;
    }
    public void setParrentId(Long parrentId) {
        this.parrentId = parrentId;
    }
    public String getParrentIds() {
        return parrentIds;
    }
    public void setParrentIds(String parrentIds) {
        this.parrentIds = parrentIds;
    }
    public Boolean getAvalible() {
        return avalible;
    }
    public void setAvalible(Boolean avalible) {
        this.avalible = avalible;
    }
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    
}
