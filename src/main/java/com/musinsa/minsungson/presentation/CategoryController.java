package com.musinsa.minsungson.presentation;

import com.musinsa.minsungson.application.CategoryService;
import com.musinsa.minsungson.presentation.dto.CreateCategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/api/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateCategoryRequest request) {
        Long id = categoryService.create(request);

        return ResponseEntity.created(URI.create("/api/categories/" + id)).build();
    }
}
