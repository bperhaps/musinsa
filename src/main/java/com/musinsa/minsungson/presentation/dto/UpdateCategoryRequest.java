package com.musinsa.minsungson.presentation.dto;

public class UpdateCategoryRequest {
    private String name;
    private Long parentId;
    private Long orderingNumber;

    protected UpdateCategoryRequest() {
    }

    public UpdateCategoryRequest(String name, Long parentId, Long orderingNumber) {
        this.name = name;
        this.parentId = parentId;
        this.orderingNumber = orderingNumber;
    }

    public String getName() {
        return name;
    }

    public Long getParentId() {
        return parentId;
    }

    public Long getOrderingNumber() {
        return orderingNumber;
    }
}
