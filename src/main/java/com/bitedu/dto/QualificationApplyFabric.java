package com.bitedu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class QualificationApplyFabric implements Serializable {

    @JsonProperty(value = "$class")
    private String className;

    private String qualificationApplyId;

    private int auditState;

    private String administrator;

    private String company;

    public QualificationApplyFabric(){

    }

    public QualificationApplyFabric(String className, String qualificationApplyId, int auditState, String administrator, String company) {
        this.className = className;
        this.qualificationApplyId = qualificationApplyId;
        this.auditState = auditState;
        this.administrator = administrator;
        this.company = company;
        this.administrator = administrator;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getQualificationApplyId() {
        return qualificationApplyId;
    }

    public void setQualificationApplyId(String qualificationApplyId) {
        this.qualificationApplyId = qualificationApplyId;
    }

    public int getAuditState() {
        return auditState;
    }

    public void setAuditState(int auditState) {
        this.auditState = auditState;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
