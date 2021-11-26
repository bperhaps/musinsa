package com.musinsa.minsungson.infrastructure;

import java.util.Objects;

public class CategoryColumns {
    private Long id;
    private String name;
    private Long orderingNumber;
    private Long parentId;

    private CategoryColumns() {
    }

    public CategoryColumns(Long id, String name, Long orderingNumber, Long parentId) {
        this.id = id;
        this.name = name;
        this.orderingNumber = orderingNumber;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getOrderingNumber() {
        return orderingNumber;
    }

    public Long getParentId() {
        return parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryColumns that = (CategoryColumns) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
