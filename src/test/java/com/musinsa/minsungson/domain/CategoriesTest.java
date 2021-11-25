package com.musinsa.minsungson.domain;

import com.musinsa.minsungson.exception.IllegalCategoryIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CategoriesTest {

    private Categories categories;

    @BeforeEach
    void setUp() {
        categories = new Categories(
                List.of(
                        new Category("test1", 0L),
                        new Category("test2", 1L),
                        new Category("test3", 2L)
                )
        );
    }

    @DisplayName("새로운 카테고리를 추가한다.")
    @ParameterizedTest
    @MethodSource("getParametersForAdd")
    void add(Category newCategory, int index, List<String> expected) {
        categories.add(newCategory, index);

        List<String> actual = categories.getValue().stream()
                .map(Category::getName)
                .collect(toList());

        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> getParametersForAdd() {
        return Stream.of(
                Arguments.of(new Category("test4"), 3, List.of("test1", "test2", "test3", "test4")),
                Arguments.of(new Category("test4"), 0, List.of("test4", "test1", "test2", "test3")),
                Arguments.of(new Category("test4"), 1, List.of("test1", "test4", "test2", "test3")),
                Arguments.of(new Category("test4"), 2, List.of("test1", "test2", "test4", "test3"))
        );
    }

    @DisplayName("add()시 index는 음수이거나 현재 list 크기보다 클 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3, 4, 5, 6})
    void addIndexCannotBeNegativeOrOverThenMaxSize(int index) {
        assertThatThrownBy(() -> categories.add(new Category("test"), index))
                .isInstanceOf(IllegalCategoryIndexException.class)
                .hasMessage("잘못된 카테고리 인덱스 번호 입니다.");
    }
}