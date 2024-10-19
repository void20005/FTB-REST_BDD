package com.m2p.at.ftbtests.api.rest.bdd.utils;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2p.at.ftbtests.api.rest.bdd.config.AppConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * A class to contain low-level calls like HTTP-GET etc.
 */
@Slf4j
public class ApiCalls {
    private final AllureRestAssured allureRestAssured;
    private final ObjectMapper objectMapper;
    private final AppConfig appConfig;
    private final RequestSpecification reqSpec;

    public ApiCalls() {
        this.allureRestAssured = BeansFactory.provideAllureRestAssured();
        this.objectMapper = BeansFactory.provideObjectMapper();
        this.appConfig = BeansFactory.provideAppConfig();
        this.reqSpec = buildReqSpec();
    }

    public <R> R doGet(int expectedStatus, Class<R> responseClass, String path, Object... pathParams) {
        log.info("Sending GET for: {}({}) ", path, Arrays.toString(pathParams));
        return given()
                .spec(this.reqSpec)
                .get(path, pathParams)
                .then().log().all()
                .and()
                .statusCode(expectedStatus)
                .extract().response().as(responseClass);
    }

    public <R> R doPost(RequestSpecification reqSpec, int expectedStatus, Class<R> responseClass,
                        String body, String path, Object... pathParams) {
        log.info("Sending POST for: {} {} | Body = {}", path, Arrays.toString(pathParams), body);
        return given()
                .spec(reqSpec)
                .body(body)
                .post(path, pathParams)
                .then().log().all()
                .and()
                .statusCode(expectedStatus)
                .extract().response().as(responseClass);
    }

    public <R> R doPost(int expectedStatus, Class<R> responseClass,
                        Object body, String path, Object... pathParams) throws JsonProcessingException {
        return doPost(this.reqSpec, expectedStatus, responseClass,
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body),
                path, pathParams);
    }

    private RequestSpecification buildReqSpec() {
        var headers = new HashMap<>(Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json",
                "X-CUSTOM", "" // Just an example of custom header usage
        ));

        //return given().baseUri("http://localhost:8080/api/v0")
        return given().baseUri(appConfig.url())
                .filter(allureRestAssured)
                .log().all()
                .headers(headers);
    }
    public <R> R doDelete(int[] expectedStatuses, Class<R> responseClass, String path, Object... pathParams) {
        log.info("Sending DELETE for: {}({})", path, Arrays.toString(pathParams));

        // Wrap each expected status with 'equalTo'
        return given()
                .spec(this.reqSpec)
                .delete(path, pathParams)
                .then().log().all()
                .and()
                .assertThat()
                .statusCode(anyOf(Arrays.stream(expectedStatuses)
                        .mapToObj(org.hamcrest.Matchers::equalTo)
                        .toArray(org.hamcrest.Matcher[]::new)))  // Ensure proper wrapping with equalTo
                .extract().response().as(responseClass);
    }



}

