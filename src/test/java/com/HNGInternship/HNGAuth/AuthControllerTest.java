package com.HNGInternship.HNGAuth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthControllerTest extends AbstractTest{
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testRegisterUserSuccessfully() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"john.doe@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"password\": \"password123\", \"phone\": \"1234567890\" }")
                .post("/auth/register");

        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body("data.user.firstName", equalTo("John"))
                .body("data.user.lastName", equalTo("Doe"))
                .body("data.user.email", equalTo("john.doe@example.com"))
                .body("data.accessToken", notNullValue());
    }

    @Test
    void testLoginUserSuccessfully() {
        // Register the user first
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"john.doe@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"password\": \"password123\", \"phone\": \"1234567890\" }")
                .post("/auth/register");

        // Attempt to login
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"john.doe@example.com\", \"password\": \"password123\" }")
                .post("/auth/login");

        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("data.user.email", equalTo("john.doe@example.com"))
                .body("data.accessToken", notNullValue());
    }

    @Test
    void testRegisterUserFailsForMissingFields() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"\", \"firstName\": \"\", \"lastName\": \"\", \"password\": \"\", \"phone\": \"\" }")
                .post("/auth/register");

        response.then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .body("errors", notNullValue());
    }

    @Test
    void testRegisterUserFailsForDuplicateEmail() {
        // Register the first user
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"duplicate@example.com\", \"firstName\": \"Jane\", \"lastName\": \"Doe\", \"password\": \"password123\", \"phone\": \"1234567890\" }")
                .post("/auth/register");

        // Attempt to register another user with the same email
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"duplicate@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"password\": \"password123\", \"phone\": \"0987654321\" }")
                .post("/auth/register");

        response.then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .body("message", equalTo("Email must be unique"));
    }
}
