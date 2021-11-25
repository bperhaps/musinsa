package com.musinsa.minsungson.acceptance;

import com.musinsa.minsungson.presentation.dto.CreateCategoryRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCategoryAcceptanceTest extends AcceptanceTest {

    @DisplayName("카테고리를 생성한다.")
    @Test
    void name() {
        CreateCategoryRequest categoryRequest = new CreateCategoryRequest("test", 1L, null);
        ExtractableResponse<Response> response = create(categoryRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
}
