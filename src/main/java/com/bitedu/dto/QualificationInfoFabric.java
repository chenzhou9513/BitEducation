package com.bitedu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QualificationInfoFabric {

    @JsonProperty(value = "$class")
    private String className;

    private String qualificationId;

    private int qualificationType;

    public QualificationInfoFabric(){}

    public QualificationInfoFabric(String className, String qualificationId, int qualificationType) {
        this.className = className;
        this.qualificationId = qualificationId;
        this.qualificationType = qualificationType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId;
    }

    public int getQualificationType() {
        return qualificationType;
    }

    public void setQualificationType(int qualificationType) {
        this.qualificationType = qualificationType;
    }
}
