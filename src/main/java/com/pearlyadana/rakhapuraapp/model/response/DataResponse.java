package com.pearlyadana.rakhapuraapp.model.response;

import java.io.Serializable;

public class DataResponse implements Serializable {

    private Integer status;

    private Integer createdCount;

    private Integer errorCount;

    public DataResponse() {
    }

    public DataResponse(Integer status, Integer createdCount, Integer errorCount) {
        this.status = status;
        this.createdCount = createdCount;
        this.errorCount = errorCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreatedCount() {
        return createdCount;
    }

    public void setCreatedCount(Integer createdCount) {
        this.createdCount = createdCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

}
