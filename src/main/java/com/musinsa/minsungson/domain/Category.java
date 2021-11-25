package com.musinsa.minsungson.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Embedded
    private Categories categories;

    @Column(name = "orderingNumber")
    private Long orderingNumber;

    protected Category() {
    }

    public Category(String name) {
        this(null, name, new Categories(new ArrayList<>()), 0L);
    }

    public Category(String name, Long orderingNumber) {
        this(null, name, new Categories(new ArrayList<>()), orderingNumber);
    }

    public Category(Long id, String name, Categories categories, Long orderingNumber) {
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.orderingNumber = orderingNumber;
    }

    public void add(Category category) {
        this.categories.add(category);
    }

    public void add(Category category, Long index) {
        this.categories.add(category, index);
    }

    protected void changeOrderingNumber(long orderingNumber) {
        this.orderingNumber = orderingNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Category> getCategories() {
        return categories.getValue();
    }

    public Long getOrderingNumber() {
        return orderingNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
