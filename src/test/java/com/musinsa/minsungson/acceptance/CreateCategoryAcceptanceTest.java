package com.musinsa.minsungson.acceptance;

import com.musinsa.minsungson.presentation.dto.CategoryResponse;
import com.musinsa.minsungson.presentation.dto.CreateCategoryRequest;
import com.musinsa.minsungson.presentation.dto.UpdateCategoryRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCategoryAcceptanceTest extends AcceptanceTest {

    @DisplayName("카테고리를 생성한다.")
    @Test
    void createCategory() {
        CreateCategoryRequest request = new CreateCategoryRequest("test", 1L, null);
        ExtractableResponse<Response> response = create(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("카테고리를 수정한다.")
    @Test
    void updateCategory() {
        CreateCategoryRequest createRequest = new CreateCategoryRequest("test", 1L, null);
        String header = create(createRequest).header(HttpHeaders.LOCATION);
        String id = extractId(header);

        UpdateCategoryRequest updateRequest = new UpdateCategoryRequest("changedName", 1L, 0L);
        int statusCode = update(Long.valueOf(id), updateRequest).statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("카테고리를 삭제한다.")
    @Test
    void deleteCategory() {
        CreateCategoryRequest createRequest = new CreateCategoryRequest("test", 1L, null);
        String header = create(createRequest).header(HttpHeaders.LOCATION);
        String id = extractId(header);

        int statusCode = delete(Long.valueOf(id)).statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("카테고리를 조회한다.")
    @Test
    void readCategories() {
        String header = create(new CreateCategoryRequest("hello", 1L, 0L))
                .header(HttpHeaders.LOCATION);
        long id = Long.parseLong(extractId(header));

        header = create(new CreateCategoryRequest("hello1", id, 0L)).header(HttpHeaders.LOCATION);
        long id2 = Long.parseLong(extractId(header));

        create(new CreateCategoryRequest("hello2", id2, 0L)).header(HttpHeaders.LOCATION);

        CategoryResponse response = read(id).as(CategoryResponse.class);
        assertThat(response.getCategories()).hasSize(1);
        assertThat(response.getCategories().get(0).getCategories()).hasSize(1);
    }

    private String extractId(String header) {
        String[] splitLocation = header.split("/");
        String id = splitLocation[splitLocation.length-1];
        return id;
    }
}
