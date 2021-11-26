package com.musinsa.minsungson.application;

import com.musinsa.minsungson.domain.Category;
import com.musinsa.minsungson.domain.CategoryRepository;
import com.musinsa.minsungson.domain.ReadonlyCategoryRepository;
import com.musinsa.minsungson.exception.CategoryNotFoundException;
import com.musinsa.minsungson.exception.ParentNotFoundException;
import com.musinsa.minsungson.presentation.dto.CategoryResponse;
import com.musinsa.minsungson.presentation.dto.CreateCategoryRequest;
import com.musinsa.minsungson.presentation.dto.UpdateCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
public class CategoryService {
    private static final long ROOT_ID = 1L;

    private final CategoryRepository categoryRepository;
    private final ReadonlyCategoryRepository readonlyCategoryRepository;

    public CategoryService(CategoryRepository categoryRepository, ReadonlyCategoryRepository readonlyCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.readonlyCategoryRepository = readonlyCategoryRepository;
    }

    public Long create(CreateCategoryRequest request) {
        Category category = findCategoryByParentId(request.getParentId());
        Category newCategory = categoryRepository.save(new Category(request.getName(), request.getIndex()));

        category.add(newCategory, request.getIndex());

        readonlyCategoryRepository.save(new Category(newCategory.getId(), request.getName(), request.getIndex(), request.getParentId()));

        return newCategory.getId();
    }

    public CategoryResponse read(Long id) {
        Category category = readonlyCategoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        return toCategoryResponse(category);
    }

    private CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                toCategoryResponses(category.getCategories())
        );
    }

    private List<CategoryResponse> toCategoryResponses(List<Category> categories) {
        return categories.stream()
                .map(this::toCategoryResponse)
                .collect(toList());
    }

    public void update(Long id, UpdateCategoryRequest request) {
        Category category = findCategoryById(id);

        Category originalParent = findCategoryByParentId(category.getParentId());
        Category newParent = findCategoryByParentId(request.getParentId());

        originalParent.removeChild(category);
        newParent.add(category, request.getOrderingNumber());

        category.changeName(request.getName());
        readonlyCategoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = findCategoryById(id);
        categoryRepository.deleteById(id);
        readonlyCategoryRepository.delete(category);
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
