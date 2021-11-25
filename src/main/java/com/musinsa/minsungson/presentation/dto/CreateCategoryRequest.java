package com.musinsa.minsungson.presentation.dto;

public class CreateCategoryRequest {

    private String name;
    private Long parentId;
    private Long index;

    private CreateCategoryRequest() {
    }

    public CreateCategoryRequest(String name, Long parentId, Long index) {
        this.name = name;
        this.parentId = parentId;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public Long getParentId() {
        return parentId;
    }

    public Long getIndex() {
        return index;
    }
}
