package api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestAPI {
    private static final String BASE_URI = "http://qa-scooter.praktikum-services.ru";

    public RequestSpecification requestSpecification(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .build()
                .filter(new AllureRestAssured())
                .log().all();
    }

}
