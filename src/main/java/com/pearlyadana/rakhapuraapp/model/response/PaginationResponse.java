package com.pearlyadana.rakhapuraapp.model.response;

import java.util.List;

public final class PaginationResponse<T> {

    private List<T> elements;
    private long totalElements = 0;
    private int totalPages = 0;
    private int pageSize = 0;

    public PaginationResponse() {
    }

    public PaginationResponse(List<T> elements, long totalElements, int totalPages, int pageSize) {
        this.elements = elements;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
    }

    public PaginationResponse<T> addList(List<T> elements) {
        this.elements = elements;
        return this;
    }

    public PaginationResponse<T> addTotalElements(long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public PaginationResponse<T> addTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public PaginationResponse<T> addPageSize(int pageSize) {
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

}
