package api;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.CourierData;

import static io.restassured.RestAssured.given;

public class CourierAPI extends RestAPI{

    public static final String CREATE_COURIER_URI = "/api/v1/courier";
    public static final String LOGIN_COURIER_URI = "/api/v1/courier/login";
    public static final String DELETE_COURIER_URI = "/api/v1/courier/";

    // Response fields
    public static final String OK_FIELD = "ok";
    public static final String MESSAGE_FIELD = "message";
    public static final String LOGIN_ID_FIELD = "id";
    public static final String LOGIN_ID_PARAM = "id";

    //Error messages
    public static final String INSUFFICIENT_INFORMATION = "Недостаточно данных для создания учетной записи";
    public static final String INSUFFICIENT_LOGIN_DATA= "Недостаточно данных для входа";
    public static final String WRONG_LOGIN_DATA= "Учетная запись не найдена";
    public static final String LOGIN_EXISTS= "Этот логин уже используется. Попробуйте другой.";
    public static final String ID_DOESNT_EXISTS= "Курьера с таким id нет.";
    public static final String INSUFFICIENT_DELETE_DATA= "Недостаточно данных для удаления курьера";


    public static final int EMPTY_ID = -1;
    public static final int DUMMY_ID = 0000;

    @Step("Create courier")
    @Description("Create courier with data parameter")
    public ValidatableResponse createCourier(CourierData data){
        return given()
                .spec(requestSpecification())
                .and()
                .body(data)
                .when()
                .post(CREATE_COURIER_URI)
                .then();
    }
    @Step("Login courier")
    @Description("Login courier with data parameter")
    public ValidatableResponse loginCourier(CourierData data){
        return given()
                .spec(requestSpecification())
                .and()
                .body(data)
                .when()
                .post(LOGIN_COURIER_URI)
                .then();
    }

    @Step("Delete courier")
    @Description("Delete courier with Id")
    public ValidatableResponse deleteCourier(int courierId){
        return given()
                .spec(requestSpecification())
                .when()
                .delete(DELETE_COURIER_URI + courierId)
                .then();
    }

    @Step("Delete courier")
    @Description("Delete courier without Id")
    public ValidatableResponse deleteCourier(){
        return given()
                .spec(requestSpecification())
                .when()
                .delete(DELETE_COURIER_URI)
                .then();
    }

}
