package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;

public class GradeDto implements Serializable {

    private Long id;

    private String name;

    private String remark;

    private String abbreviate;

    private boolean authorizedStatus;

    private Long authorizedUserId;

    public GradeDto() {
    }

    public GradeDto(Long id, String name, String remark, String abbreviate, boolean authorizedStatus, Long authorizedUserId) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.abbreviate = abbreviate;
        this.authorizedStatus = authorizedStatus;
        this.authorizedUserId = authorizedUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAbbreviate() {
        return abbreviate;
    }

    public void setAbbreviate(String abbreviate) {
        this.abbreviate = abbreviate;
    }

    public boolean isAuthorizedStatus() {
        return authorizedStatus;
    }

    public void setAuthorizedStatus(boolean authorizedStatus) {
        this.authorizedStatus = authorizedStatus;
    }

    public Long getAuthorizedUserId() {
        return authorizedUserId;
    }

    public void setAuthorizedUserId(Long authorizedUserId) {
        this.authorizedUserId = authorizedUserId;
    }

}
