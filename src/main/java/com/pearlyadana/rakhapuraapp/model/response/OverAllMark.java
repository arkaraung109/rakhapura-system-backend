package com.pearlyadana.rakhapuraapp.model.response;

public class OverAllMark {

    private String mark;

    private String status;

    public OverAllMark() {
    }

    public OverAllMark(String mark, String status) {
        this.mark = mark;
        this.status = status;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmpty() {
        this.mark = "-";
        this.status = "-";
    }

}
