package com.pearlyadana.rakhapuraapp.model.response;

import java.io.Serializable;

public class CustomHttpResponse implements Serializable {

    private Integer status;
    private String message;

    public CustomHttpResponse() {
    }

    public CustomHttpResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
