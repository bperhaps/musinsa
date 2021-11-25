package com.musinsa.minsungson.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        this.category = new Category("test", 0L);
    }

    @DisplayName("새로운 하위 카테고리를 맨 뒤에 추가한다.")
    @ParameterizedTest
    @MethodSource("getParametersForAdd")
    void add(List<Category> categories, List<String> expected) {
        categories.forEach(newCategory -> category.add(newCategory));

        List<String> actual = category.getCategories().stream()
                .map(Category::getName)
                .collect(toList());

        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> getParametersForAdd() {
        return Stream.of(
                Arguments.of(
                        List.of(new Category("test2"), new Category("test3")),
                        List.of("test2", "test3")
                ),
                Arguments.of(
                        List.of(new Category("test2")),
                        List.of("test2")
                ),
                Arguments.of(
                        List.of(new Category("test2"), new Category("test3"), new Category("test4")),
                        List.of("test2", "test3", "test4")
                )
        );
    }

    @DisplayName("새로운 하위 카테고리를 특정 인덱스에 삽입한다.")
    @ParameterizedTest
    @MethodSource("getParametersForAddInIndex")
    void addInIndex(List<Category> categories, List<String> expected) {
        categories.forEach(newCategory -> category.add(newCategory, newCategory.getOrderingNumber().intValue()));

        List<String> actual = category.getCategories().stream()
                .map(Category::getName)
                .collect(toList());

        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> getParametersForAddInIndex() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Category("test1", 0L),
                                new Category("test2", 0L)
                        ),
                        List.of("test2", "test1")
                ),
                Arguments.of(
                        List.of(
                                new Category("test1", 0L),
                                new Category("test2", 0L),
                                new Category("test3", 2L)
                        ),
                        List.of("test2", "test1", "test3")
                ),
                Arguments.of(
                        List.of(
                                new Category("test1", 0L),
                                new Category("test2", 0L),
                                new Category("test2", 2L),
                                new Category("test3", 3L)
                        ),
                        List.of("test2", "test1", "test2", "test3")
                )
        );
    }

    @DisplayName("정렬 번호를 변경한다.")
    @Test
    void changeOrderingNumber() {
        category = new Category("test", 3L);
        category.changeOrderingNumber(1L);

        assertThat(category.getOrderingNumber()).isEqualTo(1L);
    }
}
