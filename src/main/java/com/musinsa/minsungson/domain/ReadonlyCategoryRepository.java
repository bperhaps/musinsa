package com.musinsa.minsungson.domain;

import java.util.List;
import java.util.Optional;

public interface ReadonlyCategoryRepository {
    void saveAll(List<Category> categories);
    void save(Category category);
    Optional<Category> findById(Long id);
    void delete(Category category);
}
