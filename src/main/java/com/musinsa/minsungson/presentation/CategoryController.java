package com.musinsa.minsungson.presentation;

import com.musinsa.minsungson.application.CategoryService;
import com.musinsa.minsungson.presentation.dto.CategoryResponse;
import com.musinsa.minsungson.presentation.dto.CreateCategoryRequest;
import com.musinsa.minsungson.presentation.dto.UpdateCategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> read(@PathVariable Long id) {
        CategoryResponse response = categoryService.read(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateCategoryRequest request) {
        categoryService.update(id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
