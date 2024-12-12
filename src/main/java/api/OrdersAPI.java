package api;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.OrderData;

import static io.restassured.RestAssured.given;

public class OrdersAPI extends RestAPI {
    public static final String CREATE_ORDER_URI = "/api/v1/orders";
    public static final String CANCEL_ORDER_URI = "/api/v1/orders/cancel";
    public static final String ACCEPT_ORDER_URI = "/api/v1/orders/accept/";
    public static final String TRACK_ORDER_URI = "/api/v1/orders/track";

    //request params
    public static final String ORDER_ID_PARAM = "id";
    public static final String COURIER_ID_PARAM = "courierId";
    public static final String TRACK_PARAM = "t";

    // response fields
    public static final String ORDER_TRACK_FIELD = "track";
    public static final String ORDERS_FIELD = "orders";
    public static final String ORDER_OK_FIELD = "ok";
    public static final String ORDER_TRACK_PARAM = "track";
    public static final String ORDER_ID_FIELD = "order.id";
    public static final String ORDER_MESSAGE_FIELD = "message";

    //error messages
    public static final String INSUFFICIENT_INFORMATION = "Недостаточно данных для поиска";
    public static final String ID_DOESNOT_EXIST = "Курьера с таким id не существует";
    public static final String ORDER_DOESNOT_EXIST = "Заказа с таким id не существует";
    public static final String ORDER_NOT_FOUND = "Заказ не найден";


    public static final int RENT_DATA_OFFSET = 10;
    public static final int EMPTY_TRACK = -1;
    public static final int DUMMY_TRACK = 0;

    @Step("Create order")
    @Description("Create order")
    public ValidatableResponse createOrder(OrderData data) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(data)
                .when()
                .post(CREATE_ORDER_URI)
                .then();
    }

    @Step("Cancel order")
    @Description("Cancel order")
    public ValidatableResponse cancelOrder(int param) {
        String jsonParam = "{\"" + ORDER_TRACK_PARAM + "\":" + param + "}";
        return given()
                .spec(requestSpecification())
                .and()
                .body(jsonParam)
                .when()
                .put(CANCEL_ORDER_URI)
                .then();
    }

    @Step("Accept order with courier and track id")
    @Description("Accept order")
    public ValidatableResponse acceptOrder(int id, int courierId) {
        return given()
                .spec(requestSpecification())
                .and()
                .queryParam(OrdersAPI.COURIER_ID_PARAM, courierId)
                .when()
                .put(ACCEPT_ORDER_URI + id)
                .then();
    }

    @Description("Accept order with courier")
    public ValidatableResponse acceptOrderWithoutOrderId(int courierId) {
        return given()
                .spec(requestSpecification())
                .and()
                .queryParam(OrdersAPI.COURIER_ID_PARAM, courierId)
                .when()
                .put(ACCEPT_ORDER_URI)
                .then();
    }

    @Step("Accept order with track id")
    @Description("Accept order without courierId")
    public ValidatableResponse acceptOrder(int id) {
        return given()
                .spec(requestSpecification())
                .when()
                .put(ACCEPT_ORDER_URI + id)
                .then();
    }

    @Step("Track order by id")
    @Description("track order")
    public ValidatableResponse trackOrder(int track) {
        return given()
                .spec(requestSpecification())
                .and()
                .queryParam(OrdersAPI.TRACK_PARAM, track)
                .when()
                .get(TRACK_ORDER_URI)
                .then();
    }

    @Step("Track order without id")
    @Description("track order without track ID")
    public ValidatableResponse trackOrder() {
        return given()
                .spec(requestSpecification())
                .and()
                .when()
                .get(TRACK_ORDER_URI)
                .then();
    }

    @Step("Extract order Id")
    @Description("Get order Id with track")
    public int getOrderIdByTrack(int track) {
        return trackOrder(track).extract().path(OrdersAPI.ORDER_ID_FIELD);
    }
}
