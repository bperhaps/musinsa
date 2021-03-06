package com.musinsa.minsungson.domain;

import com.musinsa.minsungson.exception.IllegalCategoryIndexException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Comparator.comparingLong;

@Embeddable
public class Categories {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "parent_id")
    private List<Category> value;

    protected Categories() {
    }

    public Categories(List<Category> value) {
        this.value = new ArrayList<>(value);

    }

    public void add(Category category) {
        add(category, (long) value.size());
    }

    public void add(Category category, Long index) {
        orderCategories();
        addCategory(category, index);
        changeOrderingNumber();
    }

    private void addCategory(Category category, Long index) {
        if(Objects.isNull(index)) {
            addCategoryToValue(category, value.size());
            return;
        }
        addCategoryToValue(category, index.intValue());
    }

    private void addCategoryToValue(Category category, int index) {
        try {
            this.value.add(index, category);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalCategoryIndexException();
        }
    }

    private void orderCategories() {
        this.value.sort(comparingLong(Category::getOrderingNumber));
    }

    private void changeOrderingNumber() {
        for (int i = 0; i < value.size(); i++) {
            value.get(i).changeOrderingNumber(i);
        }
    }

    public void remove(Category category) {
        this.value.remove(category);
        changeOrderingNumber();
    }

    public List<Category> getValue() {
        return value;
    }
}
