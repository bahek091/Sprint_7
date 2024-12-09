import api.OrdersAPI;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class SearchOrderTest extends OrderBaseTest {

    @Test
    public void canFindOrderTest() {
        orderData = getFilledOrderData();
        orderTrack = ordersApi.createOrder(orderData)
                .log().all()
                .body(OrdersAPI.ORDER_TRACK_FIELD, notNullValue())
                .extract().path(OrdersAPI.ORDER_TRACK_FIELD);
        ordersApi.trackOrder(orderTrack)
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body(OrdersAPI.ORDER_ID_FIELD, notNullValue());
    }

    @Test
    public void cannotFindOrderWithoutTrackTest() {
        ordersApi.trackOrder()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body(OrdersAPI.ORDER_MESSAGE_FIELD, is(OrdersAPI.INSUFFICIENT_INFORMATION));
    }

    @Test
    public void cannotFindOrderWithDummyTrackTest() {
        ordersApi.trackOrder(OrdersAPI.DUMMY_TRACK)
                .log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body(OrdersAPI.ORDER_MESSAGE_FIELD, is(OrdersAPI.ORDER_NOT_FOUND));
    }
}
