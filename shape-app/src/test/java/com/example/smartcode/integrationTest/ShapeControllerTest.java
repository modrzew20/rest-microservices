package com.example.smartcode.integrationTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class ShapeControllerTest {

    @LocalServerPort
    private int port;

    private String creatorBearerToken;
    private String adminBearerToken;
    private String creatorWithNoAccessBearerToken;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        creatorBearerToken = RestAssured.given().contentType(ContentType.URLENC)
                .formParam("username", "creator")
                .formParam("password", "password")
                .when().post("/api/v1/login").then().statusCode(200).extract().path("accessToken");

        adminBearerToken = RestAssured.given().contentType(ContentType.URLENC)
                .formParam("username", "admin")
                .formParam("password", "password")
                .when().post("/api/v1/login").then().statusCode(200).extract().path("accessToken");

        creatorWithNoAccessBearerToken = RestAssured.given().contentType(ContentType.URLENC)
                .formParam("username", "creatorWithNoAccess")
                .formParam("password", "password")
                .when().post("/api/v1/login").then().statusCode(200).extract().path("accessToken");
    }

    @Test
    void getAllTest() {
        RestAssured.get("/api/v1/shapes").then().statusCode(200);
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "{\"type\":\"CIRCLE\",\"parameters\":[2.3]}",
            "{\"type\":\"CIRCLE\",\"parameters\":[4.8]}",
            "{\"type\":\"CIRCLE\",\"parameters\":[234]}",
            "{\"type\":\"CIRCLE\",\"parameters\":[20000]}",
            "{\"type\":\"CIRCLE\",\"parameters\":[45]}",
            "{\"type\":\"RECTANGLE\",\"parameters\":[10.66666, 20]}",
            "{\"type\":\"RECTANGLE\",\"parameters\":[10, 20.6666]}",
            "{\"type\":\"RECTANGLE\",\"parameters\":[546456, 464564]}",
            "{\"type\":\"RECTANGLE\",\"parameters\":[0.45, 0.20]}",
            "{\"type\":\"SQUARE\",\"parameters\":[13765.4]}",
            "{\"type\":\"SQUARE\",\"parameters\":[0.4]}",
            "{\"type\":\"SQUARE\",\"parameters\":[13.4]}",

    })
    void createTest(String shape) {

        int size = RestAssured.get("/api/v1/shapes").then().statusCode(200).extract().path("size()");
        System.out.println(shape);
        RestAssured.given().contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body(shape)
                .post("/api/v1/shapes")
                .then().statusCode(201)
                .extract().response();

        RestAssured.get("/api/v1/shapes").then().statusCode(200).body("size()", is(size + 1));

    }


    @ParameterizedTest
    @ValueSource(strings = {
            "{\"type\":\"SQUARE\",\"parameters\":[-13.4]}",
            "{\"type\":\"SQUARE\",\"parameters\":[0]}",
            "{\"type\":\"CIRCLE\",\"parameters\":[-14]}",
            "{\"type\":\"CIRCLE\",\"parameters\":[0]}",
            "{\"type\":\"RECTANGLE\",\"parameters\":[-10.66666, 20]}",
            "{\"type\":\"RECTANGLE\",\"parameters\":[10, -20.6666]}",
            "{\"type\":\"RECTANGLE\",\"parameters\":[-546456, -464564]}",
    })
    void createInvalidValueOfParameterExceptionTest(String shape) {

        int size = RestAssured.get("/api/v1/shapes").then().statusCode(200).extract().path("size()");

        RestAssured.given().contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body(shape)
                .post("/api/v1/shapes")
                .then().statusCode(403)
                .extract().response();

        RestAssured.get("/api/v1/shapes").then().statusCode(200).body("size()", is(size));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "{\"type\":\"SQUARE\"}",
            "{\"type\":\"SQUARE\",\"parameters\":[5,6]}",
            "{\"type\":\"CIRCLE\"}",
            "{\"type\":\"CIRCLE\",\"parameters\":[2,6]}",
            "{\"type\":\"RECTANGLE\",\"parameters\":[20]}",
            "{\"type\":\"RECTANGLE\",\"parameters\":[10, 50,6666]}",
    })
    void createInvalidAmountOfParametersTest(String shape) {

        int size = RestAssured.get("/api/v1/shapes").then().statusCode(200).extract().path("size()");

        RestAssured.given().contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body(shape)
                .post("/api/v1/shapes")
                .then().statusCode(403)
                .extract().response();

        RestAssured.get("/api/v1/shapes").then().statusCode(200).body("size()", is(size));
    }


    @Test
    void updateCircleTest() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"CIRCLE\",\"parameters\":[2.3]}")
                .post("/api/v1/shapes");

        String createdShapeUUID = response.then().extract().path("id");
        String etag = response.header("ETag");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .header("If-Match", etag)
                .body("{\"parameters\":[2.4]}")
                .put("/api/v1/shapes/" + createdShapeUUID)
                .then().statusCode(200)
                .body("radius", is(2.4F));
    }

    @Test
    void updateSquareTest() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"SQUARE\",\"parameters\":[2.3]}")
                .post("/api/v1/shapes");

        String createdShapeUUID = response.then().extract().path("id");
        String etag = response.header("ETag");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .header("If-Match", etag)
                .body("{\"parameters\":[2.4]}")
                .put("/api/v1/shapes/" + createdShapeUUID)
                .then().statusCode(200)
                .body("width", is(2.4F));
    }


    @Test
    void updateRectangleTest() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"RECTANGLE\",\"parameters\":[2.3,3.6]}")
                .post("/api/v1/shapes");

        String createdShapeUUID = response.then().extract().path("id");
        String etag = response.header("ETag");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .header("If-Match", etag)
                .body("{\"parameters\":[2.4,5.6]}")
                .put("/api/v1/shapes/" + createdShapeUUID)
                .then().statusCode(200)
                .body("length", is(2.4F))
                .body("width", is(5.6F));
    }

    @Test
    void updateRectangleByAnotherCreatorTest() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"RECTANGLE\",\"parameters\":[2.3,3.6]}")
                .post("/api/v1/shapes");

        String createdShapeUUID = response.then().extract().path("id");
        String etag = response.header("ETag");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorWithNoAccessBearerToken)
                .header("If-Match", etag)
                .body("{\"parameters\":[2.4,5.6]}")
                .put("/api/v1/shapes/" + createdShapeUUID)
                .then().statusCode(403);
    }

    @Test
    void updateRectangleByAdminTest() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"RECTANGLE\",\"parameters\":[2.3,3.6]}")
                .post("/api/v1/shapes");

        String createdShapeUUID = response.then().extract().path("id");
        String etag = response.header("ETag");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminBearerToken)
                .header("If-Match", etag)
                .body("{\"parameters\":[2.4,5.6]}")
                .put("/api/v1/shapes/" + createdShapeUUID)
                .then().statusCode(200);
    }

    @Test
    void updateShapeEtagInvalidTest() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"CIRCLE\",\"parameters\":[2.3]}")
                .post("/api/v1/shapes");

        String createdShapeUUID = response.then().extract().path("id");


        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .header("If-Match", "invalid")
                .body("{\"parameters\":[2.4]}")
                .put("/api/v1/shapes/" + createdShapeUUID)
                .then().statusCode(403);
    }

    @Test
    void getChangesTest() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"RECTANGLE\",\"parameters\":[2.3,3.6]}")
                .post("/api/v1/shapes");

        String createdShapeUUID = response.then().extract().path("id");
        String etag = response.header("ETag");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .header("If-Match", etag)
                .body("{\"parameters\":[2.4,5.6]}")
                .put("/api/v1/shapes/" + createdShapeUUID)
                .then().statusCode(200);


        RestAssured.given().contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .get("/api/v1/shapes/" + createdShapeUUID + "/changes")
                .then().statusCode(200).body("size()", is(2));
    }


    @Test
    void getChangesByAnotherCreator() {

        String createdShapeUUID = RestAssured.given().contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"CIRCLE\",\"parameters\":[2.3]}")
                .post("/api/v1/shapes")
                .then().statusCode(201)
                .extract().path("id");

        RestAssured.given().contentType(ContentType.JSON)
                .auth().oauth2(creatorWithNoAccessBearerToken)
                .get("/api/v1/shapes/" + createdShapeUUID + "/changes")
                .then().statusCode(403);
    }

    @Test
    void getChangesByAdminCreator() {

        String createdShapeUUID = RestAssured.given().contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"CIRCLE\",\"parameters\":[2.3]}")
                .post("/api/v1/shapes")
                .then().statusCode(201)
                .extract().path("id");

        RestAssured.given().contentType(ContentType.JSON)
                .auth().oauth2(adminBearerToken)
                .get("/api/v1/shapes/" + createdShapeUUID + "/changes")
                .then().statusCode(200);
    }

    @Test
    void getAllParametrizedTest() {

        RestAssured.given().contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"RECTANGLE\",\"parameters\":[10, 20]}")
                .post("/api/v1/shapes")
                .then().statusCode(201)
                .extract().response();
        RestAssured.given().contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"SQUARE\",\"parameters\":[5]}")
                .post("/api/v1/shapes")
                .then().statusCode(201)
                .extract().response();
        RestAssured.given().contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"SQUARE\",\"parameters\":[10]}")
                .post("/api/v1/shapes")
                .then().statusCode(201)
                .extract().response();
        RestAssured.given().contentType(ContentType.JSON)
                .auth().oauth2(creatorBearerToken)
                .body("{\"type\":\"CIRCLE\",\"parameters\":[5]}")
                .post("/api/v1/shapes")
                .then().statusCode(201)
                .extract().response();

        RestAssured.get("/api/v1/shapes?type=SQUARE&areaFrom=50&areaTo=100&createdAtFrom=2023-01-06T02:21:45.794").then().statusCode(200).body("size()", is(1));
        RestAssured.get("/api/v1/shapes?type=SQUARE&areaFrom=50&areaTo=100").then().statusCode(200).body("size()", is(1));
        RestAssured.get("/api/v1/shapes?areaFrom=50&areaTo=100").then().statusCode(200).body("size()", is(2));
        RestAssured.get("/api/v1/shapes?invalidParameter=50&areaTo=100").then().statusCode(403);

    }


}
