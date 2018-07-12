package com.demo.integration.comom.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ResponseData implements Serializable{

    private static final long serialVersionUID = -6357407935165317225L;
    
    private Integer total;
    private List<?> rows;
    
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public List<?> getRows() {
        return rows;
    }
    public void setRows(List<?> rows) {
        this.rows = rows;
    }
    
    
}
