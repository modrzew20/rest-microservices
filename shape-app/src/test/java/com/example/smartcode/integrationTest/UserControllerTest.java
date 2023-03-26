package com.example.smartcode.integrationTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class UserControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    void getAllTest() {
        RestAssured.get("/api/v1/users").then().statusCode(200);
    }

    @Test
    void createUser() {

        int size = RestAssured.get("/api/v1/users").then().statusCode(200).extract().path("totalElements");

        RestAssured.given().contentType(ContentType.JSON)
                .body("{\"firstName\":\"New\", \"lastName\":\"user\",\"login\":\"creatorNew\",\"password\":\"password\",\"role\":\"CREATOR\"}")
                .post("/api/v1/users")
                .then().statusCode(201)
                .extract().response();

        RestAssured.get("/api/v1/users").then().statusCode(200).body("totalElements", is(size + 1));

    }

    @Test
    void createBusyLoginUser() {

        int size = RestAssured.get("/api/v1/users").then().statusCode(200).extract().path("totalElements");

        RestAssured.given().contentType(ContentType.JSON)
                .body("{\"firstName\":\"New\", \"lastName\":\"user\",\"login\":\"creatorNew2\",\"password\":\"password\",\"role\":\"CREATOR\"}")
                .post("/api/v1/users")
                .then().statusCode(201)
                .extract().response();

        RestAssured.given().contentType(ContentType.JSON)
                .body("{\"firstName\":\"New\", \"lastName\":\"user\",\"login\":\"creatorNew2\",\"password\":\"password\",\"role\":\"CREATOR\"}")
                .post("/api/v1/users")
                .then().statusCode(403)
                .extract().response();

        RestAssured.get("/api/v1/users").then().statusCode(200).body("totalElements", is(size + 1));

    }
    @Test
    void createBlankFirstNameUser() {

        int size = RestAssured.get("/api/v1/users").then().statusCode(200).extract().path("totalElements");

        RestAssured.given().contentType(ContentType.JSON)
                .body("{\"firstName\":\"\", \"lastName\":\"user\",\"login\":\"creatorNew4\",\"password\":\"password\",\"role\":\"CREATOR\"}")
                .post("/api/v1/users")
                .then().statusCode(403)
                .extract().response();

        RestAssured.get("/api/v1/users").then().statusCode(200).body("totalElements", is(size));

    }

}
