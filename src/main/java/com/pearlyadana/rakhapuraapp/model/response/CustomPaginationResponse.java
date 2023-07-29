package com.pearlyadana.rakhapuraapp.model.response;

import java.util.List;

public class CustomPaginationResponse<T> {

    private List<T> elements;

    private long totalElements = 0;

    private int totalPages = 0;

    private int pageSize = 0;

    private int totalAnswered;

    private int totalNormalPassed;

    private int totalModeratePassed;

    private int totalFailed;

    private TableHeader tableHeader;

    public CustomPaginationResponse() {
    }

    public CustomPaginationResponse(List<T> elements, long totalElements, int totalPages, int pageSize, int totalAnswered, int totalNormalPassed, int totalModeratePassed, int totalFailed, TableHeader tableHeader) {
        this.elements = elements;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.totalAnswered = totalAnswered;
        this.totalNormalPassed = totalNormalPassed;
        this.totalModeratePassed = totalModeratePassed;
        this.totalFailed = totalFailed;
        this.tableHeader = tableHeader;
    }

    public CustomPaginationResponse<T> addList(List<T> elements) {
        this.elements = elements;
        return this;
    }

    public CustomPaginationResponse<T> addTotalElements(long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public CustomPaginationResponse<T> addTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public CustomPaginationResponse<T> addPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalAnswered() {
        return totalAnswered;
    }

    public void setTotalAnswered(int totalAnswered) {
        this.totalAnswered = totalAnswered;
    }

    public int getTotalNormalPassed() {
        return totalNormalPassed;
    }

    public void setTotalNormalPassed(int totalNormalPassed) {
        this.totalNormalPassed = totalNormalPassed;
    }

    public int getTotalModeratePassed() {
        return totalModeratePassed;
    }

    public void setTotalModeratePassed(int totalModeratePassed) {
        this.totalModeratePassed = totalModeratePassed;
    }

    public int getTotalFailed() {
        return totalFailed;
    }

    public void setTotalFailed(int totalFailed) {
        this.totalFailed = totalFailed;
    }

    public TableHeader getTableHeader() {
        return tableHeader;
    }

    public void setTableHeader(TableHeader tableHeader) {
        this.tableHeader = tableHeader;
    }

}
