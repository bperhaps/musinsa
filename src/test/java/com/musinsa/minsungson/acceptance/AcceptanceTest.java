package com.musinsa.minsungson.acceptance;

import com.musinsa.minsungson.presentation.dto.CreateCategoryRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected ExtractableResponse<Response> create(CreateCategoryRequest createCategoryRequest) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(createCategoryRequest)
                .when()
                .post("/api/categories")
                .then()
                .extract();
    }
}