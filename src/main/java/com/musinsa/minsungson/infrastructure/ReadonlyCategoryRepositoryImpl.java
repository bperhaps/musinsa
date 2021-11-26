package com.musinsa.minsungson.infrastructure;

import com.musinsa.minsungson.domain.Categories;
import com.musinsa.minsungson.domain.Category;
import com.musinsa.minsungson.domain.ReadonlyCategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;

@Repository
public class ReadonlyCategoryRepositoryImpl implements ReadonlyCategoryRepository {

    private final Map<Long, Set<CategoryColumns>> cacheBasedOnParentId;
    private final Map<Long, CategoryColumns> cacheBasedOnOneId;

    public ReadonlyCategoryRepositoryImpl() {
        this(new HashMap<>(), new HashMap<>());
    }

    public ReadonlyCategoryRepositoryImpl(
            Map<Long, Set<CategoryColumns>> cacheBasedOnParentId,
            Map<Long, CategoryColumns> cacheBasedOnOneId
    ) {
        this.cacheBasedOnParentId = cacheBasedOnParentId;
        this.cacheBasedOnOneId = cacheBasedOnOneId;

        save(new Category(1L, "ROOT", new Categories(new ArrayList<>()), 0L, 1L));
    }

    @Override
    public void saveAll(List<Category> categories) {
        categories.forEach(this::save);
    }

    @Override
    public void save(Category category) {
        CategoryColumns categoryColumns = new CategoryColumns(
                category.getId(),
                category.getName(),
                category.getOrderingNumber(),
                category.getParentId()
        );

        cacheBasedOnOneId
                .put(category.getId(), categoryColumns);

        if (!category.getId().equals(category.getParentId())) {
            cacheBasedOnParentId
                    .computeIfAbsent(category.getParentId(), i -> new HashSet<>())
                    .add(categoryColumns);
        }
    }

    @Override
    public Optional<Category> findById(Long id) {
        if (Objects.isNull(cacheBasedOnOneId.get(id))) {
            return Optional.empty();
        }

        return Optional.of(createCategory(id));
    }

    @Override
    public void delete(Category category) {
        CategoryColumns categoryColumns = new CategoryColumns(
                category.getId(),
                category.getName(),
                category.getOrderingNumber(),
                category.getParentId()
        );

        Set<CategoryColumns> subCategories = cacheBasedOnParentId
                .get(category.getParentId());
        if (!Objects.isNull(subCategories)) {
            subCategories.remove(categoryColumns);
        }

        cacheBasedOnOneId
                .remove(category.getId());
    }

    private Category createCategory(Long id) {
        CategoryColumns categoryColumns = cacheBasedOnOneId.get(id);

        return new Category(
                categoryColumns.getId(),
                categoryColumns.getName(),
                new Categories(createSubCategories(categoryColumns.getId())),
                categoryColumns.getOrderingNumber(),
                categoryColumns.getParentId()
        );
    }

    private List<Category> createSubCategories(Long id) {
        Set<CategoryColumns> categoryColumns = cacheBasedOnParentId.getOrDefault(id, new HashSet<>());

        return categoryColumns.stream()
                .map(CategoryColumns::getId)
                .map(this::createCategory)
                .sorted(comparingLong(Category::getOrderingNumber))
                .collect(toList());
    }
}
