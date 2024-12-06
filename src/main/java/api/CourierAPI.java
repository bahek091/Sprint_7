package api;

import io.restassured.response.Response;
import jdk.jfr.Description;
import model.CourierData;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class CourierAPI extends RestAPI{

    public static final String CREATE_COURIER_URI = "/api/v1/courier";
    public static final String LOGIN_COURIER_URI = "/api/v1/courier/login";
    public static final String DELETE_COURIER_URI = "/api/v1/courier/";

    @Description("Create courier")
    public ValidatableResponse createCourier(CourierData data){
        return given()
                .spec(requestSpecification())
                .and()
                .body(data)
                .when()
                .post(CREATE_COURIER_URI)
                .then();
    }

    @Description("Login courier")
    public Response loginCourier(CourierData data){
        return given()
                .spec(requestSpecification())
                .and()
                .body(data)
                .when()
                .post(LOGIN_COURIER_URI);
    }

    @Description("Delete courier")
    public ValidatableResponse deleteCourier(int courierId){
        return given()
                .spec(requestSpecification())
                .and()
                .param("id", courierId)
                .when()
                .delete(DELETE_COURIER_URI)
                .then();
    }

}
