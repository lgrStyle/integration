package com.demo.integration.comom.entity;

import java.io.Serializable;

public class RequestData implements Serializable{

    private static final long serialVersionUID = 1L;

    private String page;
    private String rows;
    private String sort;
    private String order;
    private String field;
    private String op;
    private String value;
    
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public String getRows() {
        return rows;
    }
    public void setRows(String rows) {
        this.rows = rows;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getOp() {
        return op;
    }
    public void setOp(String op) {
        this.op = op;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    
    
}
