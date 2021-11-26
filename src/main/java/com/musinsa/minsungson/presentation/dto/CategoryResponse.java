package com.musinsa.minsungson.presentation.dto;

import java.util.List;

public class CategoryResponse {
    private Long id;
    private String name;
    private List<CategoryResponse> categories;

    private CategoryResponse() {
    }

    public CategoryResponse(Long id, String name, List<CategoryResponse> categories) {
        this.id = id;
        this.name = name;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }
}
