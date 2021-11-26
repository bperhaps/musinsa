package com.musinsa.minsungson.infrastructure;

import com.musinsa.minsungson.domain.Category;
import com.musinsa.minsungson.exception.CategoryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class ReadonlyCategoryRepositoryImplTest {

    private ReadonlyCategoryRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new ReadonlyCategoryRepositoryImpl();
    }

    @Test
    void save() {
        Category test1 = createCategory(2L, "test1", 1L, 0);
        Category test2 = createCategory(3L, "test2", test1.getId(), 2);
        Category test3 = createCategory(4L, "test3", test1.getId(), 1);

        repository.save(test1);
        repository.save(test2);
        repository.save(test3);

        Category category = repository.findById(test1.getId())
                .orElseThrow(CategoryNotFoundException::new);

        assertThat(category.getId()).isEqualTo(2L);
        assertThat(category.getCategories()).hasSize(2);

        List<Long> subCategoryIds = category.getCategories().stream()
                .map(Category::getId)
                .collect(toList());

        assertThat(subCategoryIds).containsExactly(4L, 3L);
    }

    private Category createCategory(Long id, String name, long parentId, long orderingNumber) {
        return new Category(
                id,
                name,
                null,
                orderingNumber,
                parentId
        );
    }

    @Test
    void saveAll() {
        List<Category> categories = List.of(
                createCategory(2L, "test1", 1L, 0),
                createCategory(3L, "test2", 2L, 2),
                createCategory(4L, "test3", 3L, 1)
        );

        repository.saveAll(categories);

        Category category = repository.findById(2L)
                .orElseThrow(CategoryNotFoundException::new);

        assertThat(category.getCategories()).hasSize(1);
        assertThat(category.getCategories().get(0).getCategories()).hasSize(1);
    }

    @Test
    void delete() {
        List<Category> categories = List.of(
                createCategory(2L, "test1", 1L, 0),
                createCategory(3L, "test2", 2L, 2),
                createCategory(4L, "test3", 3L, 1)
        );

        repository.saveAll(categories);
        repository.delete(createCategory(4L, "test3", 3L, 1));
        Category category = repository.findById(2L)
                .orElseThrow(CategoryNotFoundException::new);

        assertThat(category.getCategories()).hasSize(1);
        assertThat(category.getCategories().get(0).getCategories()).isEmpty();
    }
}
