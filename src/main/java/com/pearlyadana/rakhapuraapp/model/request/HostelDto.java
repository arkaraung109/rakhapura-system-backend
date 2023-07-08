package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;

public class HostelDto implements Serializable {

    private Long id;

    private String name;

    private String address;

    private String phone;

    private boolean authorizedStatus;

    private Long authorizedUserId;

    public HostelDto() {
    }

    public HostelDto(Long id, String name, String address, String phone, boolean authorizedStatus, Long authorizedUserId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
