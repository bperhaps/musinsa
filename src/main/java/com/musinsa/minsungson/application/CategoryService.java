package com.musinsa.minsungson.application;

import com.musinsa.minsungson.domain.Category;
import com.musinsa.minsungson.domain.CategoryRepository;
import com.musinsa.minsungson.exception.ParentNotFoundException;
import com.musinsa.minsungson.presentation.dto.CreateCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CategoryService {
    private static final long ROOT_ID = 1L;

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Long create(CreateCategoryRequest createCategoryRequest) {
        Category category = findCategoryByParentId(createCategoryRequest.getParentId());
        Category newCategory = categoryRepository
                .save(new Category(createCategoryRequest.getName(), createCategoryRequest.getIndex()));
        category.add(newCategory, createCategoryRequest.getIndex());

        return newCategory.getId();
    }

    private Category findCategoryByParentId(Long id) {
        if(Objects.isNull(id) || id < 0) {
            id = ROOT_ID;
        }

        return categoryRepository.findById(id)
                .orElseThrow(ParentNotFoundException::new);
    }
}
