package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;

public class AcademicYearDto implements Serializable {

    private Long id;

    private String name;

    private boolean authorizedStatus;

    private Long authorizedUserId;

    public AcademicYearDto() {
    }

    public AcademicYearDto(Long id, String name, boolean authorizedStatus, Long authorizedUserId) {
        this.id = id;
        this.name = name;
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
