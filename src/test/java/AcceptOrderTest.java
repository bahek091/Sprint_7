import api.CourierAPI;
import api.OrdersAPI;
import model.CourierData;
import model.CourierGenerator;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class AcceptOrderTest extends OrderBaseTest{
    private CourierData courierData;

    @Test
    public void checkAcceptOrderTest(){
        orderData = getFilledOrderData();
        orderTrack = ordersApi.createOrder(orderData)
                .log().all()
                .extract().path(OrdersAPI.ORDER_TRACK_FIELD);
        courierData = CourierGenerator.getRandomCourier("User", "Pass", "Name");
        courierApi.createCourier(courierData);
        courierId = courierApi.loginCourier(courierData)
                .log().all()
                .extract().path(CourierAPI.LOGIN_ID_FIELD);
        int orderId = ordersApi.getOrderIdByTrack(orderTrack);
        ordersApi.acceptOrder(orderId, courierId)
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body(OrdersAPI.ORDER_OK_FIELD, is(true));
    }

    @Test
    public void cannotAcceptOrderWithoutCourierIdTest(){
        orderData = getFilledOrderData();
        orderTrack = ordersApi.createOrder(orderData)
                .log().all()
                .extract().path(OrdersAPI.ORDER_TRACK_FIELD);
        int orderId = ordersApi.getOrderIdByTrack(orderTrack);
        ordersApi.acceptOrder(orderId)
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body(OrdersAPI.ORDER_MESSAGE_FIELD, is(OrdersAPI.INSUFFICIENT_INFORMATION));
    }

    @Test
    public void cannotAcceptOrderWithWrongCourierIdTest(){
        orderData = getFilledOrderData();
        orderTrack = ordersApi.createOrder(orderData)
                .log().all()
                .extract().path(OrdersAPI.ORDER_TRACK_FIELD);
        int orderId = ordersApi.getOrderIdByTrack(orderTrack);
        ordersApi.acceptOrder(orderId, CourierAPI.DUMMY_ID)
                .log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .assertThat()
                .body(OrdersAPI.ORDER_MESSAGE_FIELD, is(OrdersAPI.ID_DOESNOT_EXIST));
    }

    @Test
    public void cannotAcceptOrderWithEmptyOrderIDTest(){
        courierData = CourierGenerator.getRandomCourier("User", "Pass", "Name");
        courierApi.createCourier(courierData);
        courierId = courierApi.loginCourier(courierData)
                .log().all()
                .extract().path(CourierAPI.LOGIN_ID_FIELD);
        ordersApi.acceptOrderWithoutOrderId(courierId)
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body(OrdersAPI.ORDER_MESSAGE_FIELD, is(OrdersAPI.INSUFFICIENT_INFORMATION));
    }

    @Test
    public void checkAcceptOrderWithDummyOrderIdTest(){
        courierData = CourierGenerator.getRandomCourier("User", "Pass", "Name");
        courierApi.createCourier(courierData);
        courierId = courierApi.loginCourier(courierData)
                .log().all()
                .extract().path(CourierAPI.LOGIN_ID_FIELD);
        ordersApi.acceptOrder(OrdersAPI.DUMMY_TRACK, courierId)
                .log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .assertThat()
                .body(OrdersAPI.ORDER_MESSAGE_FIELD, is(OrdersAPI.ORDER_DOESNOT_EXIST));
    }

}
