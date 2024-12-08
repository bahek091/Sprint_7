package api;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ListOfOrdersAPI extends RestAPI {
    public static final String GET_LIST_OF_ORDERS_URI = "/api/v1/orders";
    public static final String GET_LIST_OF_10_AVAILABLE_ORDERS_URI = "/api/v1/orders?limit=10";

    //request params
    public static final String GET_LIST_COURIER_ID_PARAM = "courierId";
    public static final String GET_LIST_NEAREST_STATION_PARAM = "nearestStation";
    public static final String GET_LIST_LIMIT_PARAM = "limit";
    public static final String GET_LIST_PAGE_PARAM = "page";

    @Step("Get list of orders")
    @Description("Get list of orders whit limit parameter")
    public ValidatableResponse getListOf10AvailableOrders() {
        return given()
                .spec(requestSpecification())
                .queryParam(GET_LIST_LIMIT_PARAM, 10)
                .when()
                .get(GET_LIST_OF_ORDERS_URI)
                .then();
    }

}
