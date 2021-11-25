package com.musinsa.minsungson.application;

import com.musinsa.minsungson.domain.Category;
import com.musinsa.minsungson.domain.CategoryRepository;
import com.musinsa.minsungson.exception.CategoryNotFoundException;
import com.musinsa.minsungson.exception.ParentNotFoundException;
import com.musinsa.minsungson.presentation.dto.CreateCategoryRequest;
import com.musinsa.minsungson.presentation.dto.UpdateCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CategoryService {
    private static final long ROOT_ID = 1L;

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Long create(CreateCategoryRequest request) {
        Category category = findCategoryByParentId(request.getParentId());
        Category newCategory = categoryRepository.save(new Category(request.getName(), request.getIndex()));

        category.add(newCategory, request.getIndex());

        return newCategory.getId();
    }

    public void update(Long id, UpdateCategoryRequest request) {
        Category category = findCategoryById(id);

        Category originalParent = findCategoryByParentId(category.getParentId());
        Category newParent = findCategoryByParentId(request.getParentId());

        originalParent.removeChild(category);
        newParent.add(category, request.getOrderingNumber());

        category.changeName(request.getName());
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    private Category findCategoryByParentId(Long id) {
        if (Objects.isNull(id) || id < 0) {
            id = ROOT_ID;
        }

        return categoryRepository.findById(id)
                .orElseThrow(ParentNotFoundException::new);
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }
}
