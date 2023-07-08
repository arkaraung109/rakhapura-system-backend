package com.pearlyadana.rakhapuraapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "grade")
public class Grade {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 300)
    private String name;

    @Column(name = "remark", length = 20)
    private String remark;

    @Column(name = "abbreviate", length = 15)
    private String abbreviate;

    @Column(name = "authorized_status")
    private boolean authorizedStatus;

    @Column(name = "authorized_user_id")
    private Long authorizedUserId;

    public Grade() {
    }

    public Grade(Long id, String name, String remark, String abbreviate, boolean authorizedStatus, Long authorizedUserId) {
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
