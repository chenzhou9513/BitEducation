package com.bitedu.pojo.fabric;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Administrator implements Serializable {


    @JsonProperty(value = "$class")
    private String className;

    private String adminId;

    private Integer level;

    public Administrator(String className, String adminId, Integer level) {
        this.className = className;
        this.adminId = adminId;
        this.level = level;
    }
    public Administrator(){}

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
